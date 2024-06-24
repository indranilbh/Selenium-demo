package module_1.submodule_1.automationtesting;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import base.ExtentManager;
import base.Hooks;
import pageObjects.*;


@Listeners(resources.Listeners.class)
public class Testblock extends Hooks{
	public Testblock() throws IOException {
		super();	
	}

	@Test
	public void addRemoveItem() throws IOException {
		
		ExtentManager.log("Starting ******...");
		// creating an object of the automationtesting.co.uk webpage
		Homepage home = new Homepage();
		ExtentManager.pass("comment *********");
	}
		
}
