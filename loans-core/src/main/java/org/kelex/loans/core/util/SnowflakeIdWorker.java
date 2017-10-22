package org.kelex.loans.core.util;

/**
 * Created by hechao on 2017/8/28.
 */
public class SnowflakeIdWorker extends IdWorker {

    private final static int BITS = 63;

    private final static int SEQ_MAX_BITS = 12;

    private final static int SEQ_MIN_BITS = 2;

    private final static int TIMESTAMP_MIN_BITS = 41;

    private final static int WORKER_ID_MAX_BITS = 8;

    private final static int ID_MIN_BITS = 5;

    private final long TWEPOCH = 1483200000000L;

    private final int SEQUENCE_BITS;

    private final int WORKER_ID_BITS;

    private final int CLUSTER_ID_BITS;

    private long lastTimestamp = -1L;

    private long timestamp = -1L;

    private int sequence = 0;

    private final int SEQUENCE_MASK;

    private final int MAX_WORKER_ID;

    private final int MAX_CLUSTER_ID;

    private final long WORKER_ID;

    private final long CLUSTER_ID;

    public SnowflakeIdWorker(Long clusterId, Long workerId, int sequenceBits) {

        if (sequenceBits > SEQ_MAX_BITS || sequenceBits < SEQ_MIN_BITS) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", sequenceBits));
        }

        if (clusterId == null) {
            this.CLUSTER_ID = 0;
            this.CLUSTER_ID_BITS = 0;
            this.MAX_CLUSTER_ID = 0;
        } else {
            this.CLUSTER_ID = clusterId.longValue();
            this.CLUSTER_ID_BITS = ID_MIN_BITS;
            this.MAX_CLUSTER_ID = -1 << CLUSTER_ID_BITS ^ -1;
        }

        if (this.CLUSTER_ID > MAX_CLUSTER_ID || this.CLUSTER_ID < 0) {
            throw new IllegalArgumentException(String.format("cluster Id can't be greater than %d or less than 0", MAX_CLUSTER_ID));
        }

        if (this.CLUSTER_ID_BITS == 0) {
            this.WORKER_ID_BITS = WORKER_ID_MAX_BITS;
        } else {
            this.WORKER_ID_BITS = ID_MIN_BITS;
        }

        this.MAX_WORKER_ID = -1 << WORKER_ID_BITS ^ -1;

        this.WORKER_ID = workerId.longValue();

        if (this.WORKER_ID > this.MAX_WORKER_ID || this.WORKER_ID < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }

        this.SEQUENCE_BITS = sequenceBits;

        this.SEQUENCE_MASK = -1 << sequenceBits ^ -1;

    }

    public SnowflakeIdWorker(Long workerId, int sequenceBits) {
        this(null, workerId, sequenceBits);
    }

    public SnowflakeIdWorker(Long workerId) {
        this(null, workerId, SEQ_MAX_BITS);
    }

    public SnowflakeIdWorker(Long clusterId, Long workerId) {
        this(clusterId, workerId, SEQ_MAX_BITS);
    }

    @Override
    public synchronized Long nextId() {

        timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = tilNextMillis(timestamp);
            }
        }

        lastTimestamp = timestamp;

        return (((timestamp - TWEPOCH) << CLUSTER_ID_BITS | CLUSTER_ID) << WORKER_ID_BITS | WORKER_ID) << SEQUENCE_BITS | sequence;
    }

    @Override
    public Long workerId() {
        return WORKER_ID;
    }

    @Override
    public Long clusterId() {
        return CLUSTER_ID_BITS == 0 ? null : CLUSTER_ID;
    }

    private long tilNextMillis(long timestamp) {
        while (timestamp == this.lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}
