package org.kelex.loans;

import org.kelex.loans.core.config.LoansConfiguration;
import org.kelex.loans.core.context.ApplicationContextLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties(LoansConfiguration.class)
public class Bootstrap extends ApplicationContextLoader{

	@Inject
	private Environment env;

    private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

    @PostConstruct
	public void initApplication(){

		try {
			long serverId = Long.valueOf(env.getProperty(ServerConstants.PROFILE_SERVER_ID));
			String address = InetAddress.getLocalHost().getHostAddress();
			if(log.isInfoEnabled()){
                log.info("\n---------------------------------------------------------------------\n" +
								"\tServer Id : {}\n" +
								"\tServer IP : {}\n" +
								"\tSpring Profiles : {}\n" +
								"\tRuntime : {}\n"
						, serverId
						, address
						, env.getActiveProfiles()
						, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			}

        }catch (NumberFormatException nfex){
			throw new IllegalArgumentException();
		}catch (UnknownHostException uhex){
			throw new IllegalArgumentException();
		}

		this.hashCode();
	}

	public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }
}
