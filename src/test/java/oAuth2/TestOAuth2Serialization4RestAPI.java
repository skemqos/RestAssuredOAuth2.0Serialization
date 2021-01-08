package oAuth2;

import static io.restassured.RestAssured.given;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;

public class TestOAuth2Serialization4RestAPI {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		/*
		 * System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
		 * WebDriver driver= new ChromeDriver();
		 * //driver.get("https://rahulshettyacademy.com/AutomationPractice/");
		 * driver.get(
		 * "https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss"
		 * );
		 * 
		 * driver.findElement(By.cssSelector("[type='email']")).sendKeys("dfsf");
		 * driver.findElement(By.cssSelector("[type='email']")).sendKeys(Keys.ENTER);
		 * Thread.sleep(3000);
		 * driver.findElement(By.cssSelector("[type='password']")).sendKeys("vxcvd");
		 * driver.findElement(By.cssSelector("[type='password']")).sendKeys(Keys.ENTER);
		 * Thread.sleep(5000); 
		 * String url=driver.getCurrentUrl();
		 */

		// NOTE: Pre-requisite
		//GENERATE NEW TOKEN in BELOW URL EVERY TIME ITS WILL BE RUN (workaround for google automation requirements)
		//https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss
		String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AY0e-g7UsY2fA-FBRP8lHNZsIP_78ndsS7sKEI2MeUSXm_6ndxiVuvTpS4FD8_3spXynVA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		System.out.println(url);
		String partialcode = url.split("code=")[1];
		String code = partialcode.split("&scope")[0];

		System.out.println(code);

		String response = given().urlEncodingEnabled(false).queryParams("code", code)

				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("grant_type", "authorization_code").queryParams("state", "verifyfjdss")
				.queryParams("session_state", "ff4a89d1f7011eb34eef8cf02ce4353316d9744b..7eb8")
				// .queryParam("scope",
				// "email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email")

				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		// System.out.println(response);
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		System.out.println(accessToken);
		String r2 = given().contentType("application/json").queryParams("access_token", accessToken).expect()
				.defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php").asString();
		System.out.println(r2);
	}
}
