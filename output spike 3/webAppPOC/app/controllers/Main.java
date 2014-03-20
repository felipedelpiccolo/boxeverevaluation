package controllers;

import jobs.MainJob;
import play.libs.F.Promise;
import play.libs.WS;
import play.mvc.Controller;
import services.MainService;

public class Main extends Controller {

	public static void home() {
		play.Logger.info("Request recieved on Main-home");
		new MainJob().now();
		play.Logger.info("Rendering page completed");
		render();
	}
	
	public static void longProcess() {
		final Promise<Long> promise = new MainJob().now();
		final Long result = await(promise);
		render(result);
	}
	
}
