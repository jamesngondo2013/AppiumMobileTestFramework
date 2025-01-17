package com.qa.stepdef;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.ServerManager;
import com.qa.utils.VideoManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.IOException;

public class Hooks {

    @Before
    public void initialize() throws Exception {
    	/*
       GlobalParams params = new GlobalParams();
        params.initializeGlobalParams();

        ThreadContext.put("ROUTINGKEY", params.getPlatformName() + "_"
                + params.getDeviceName());

        new ServerManager().startServer();
        new DriverManager().initializeDriver();
        */
        new VideoManager().startRecording();
    }
    
	@AfterStep
	public void afterStep(Scenario scenario) {
		
		try {
			if (scenario.isFailed()) {
				System.out.println(scenario.getName()+" is failed!");
				TakesScreenshot ts =(TakesScreenshot) new DriverManager().getDriver();
				final byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png", scenario.getName());
			}

		} catch (Exception e) {
			System.out.println("Error capturing screenshot "+ e.getMessage());
		}	
	}

    @After
    public void quit(Scenario scenario) throws IOException {
       
        new VideoManager().stopRecording(scenario.getName());
        /*
        DriverManager driverManager = new DriverManager();
        if(driverManager.getDriver() != null){
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if(serverManager.getServer() != null){
            serverManager.getServer().stop();
        }
        */
    }
}
