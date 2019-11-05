package com.first;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.function.*;

public class UseStreams {

	static WebDriver driver;

	static LinkedHashMap<String, Integer> mapOfCountOfErrors = new LinkedHashMap<String, Integer>();
	public void dummyTests() {
		
		String Name;
		
		String No;
		
		Name="vamsi";
		No="1";
		System.out.println(Name + No);
		
	}

	public static void main(String[] args) {

		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\vempv\\Downloads\\chromedriver_win32\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();

		// driver.manage().timeouts().implicitlyWait(arg0, arg1)

		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

		String[] listofBuild = { "566", "568","567","568","569","570","571" };
		HashMap<String, Integer> localList = null;
		List<String> stringsreturnvalues = null;
		UseStreams useStreams = null;
		for (String build : listofBuild) {

			driver.get("https://ci.pega.io/platform-util/job/prpc-platform-validate-branch-for-long-running-test/"
					+ build + "/testReport/");

			useStreams = new UseStreams();

			By loc = By.xpath(
					"//*[@class='pane sortable bigtable stripped']//tbody[1]//tr//td[contains(@class,'pane no-wrap')]/a[3]");
			// By loc = By.xpath("//*[@class='pane sortable bigtable
			// stripped']//tbody[1]//tr/td[1]");

			stringsreturnvalues = useStreams.textValues(loc);

			localList =	useStreams.CountofErrors(stringsreturnvalues);

		}
		
	

		

		for (Map.Entry<String, Integer> entry : localList.entrySet()) {
			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

		}

		Set<String> keys = localList.keySet();

		for (String str : keys) {
			//System.out.println("Str from the retrieved hashmap" + str);
		}

		Collection<Integer> values = localList.values();

		for (Integer intvalue : values) {
			//System.out.println("Integer from the retrieved hashmap" + intvalue);
		}

		for (String string : stringsreturnvalues) {
			// System.out.println(string.trim());
		}

		for (int i = 1; i < stringsreturnvalues.size() - 1; i++) {
			// System.out.println(stringsreturnvalues.get(i));
		}

		// driver.close();
		driver.quit();

		// List<WebElement> elist= driver.findElements(By.xpath("//*[@class='pane
		// sortable bigtable stripped']//tbody[1]/tr"));
		// elist.stream().iterator().

	}

	private List<String> textValues(By loc) {
		return getValues(loc, e -> e.getText());
	}

	private List<String> getValues(By loc, Function<WebElement, String> pred) {

		List<WebElement> elements = driver.findElements(loc);

		List<String> values = elements.stream().map(pred).collect(Collectors.toList());

		return values;

	}

	/*
	 * private List<String> attrValues(By loc, String name) { return getValues(loc,
	 * e -> e.getAttribute(name)); }
	 */

	private LinkedHashMap<String, Integer> CountofErrors(List<String> listString) {

		for (String string : listString) {
			if (mapOfCountOfErrors.containsKey(string)) {
				mapOfCountOfErrors.put(string, mapOfCountOfErrors.get(string) + 1);
			} else {
				mapOfCountOfErrors.put(string, 1);
			}
		}
		return mapOfCountOfErrors;

	}
}
