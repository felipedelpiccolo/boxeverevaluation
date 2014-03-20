package services;

import play.libs.F.Promise;

public class MainService {

	
	public Promise<String> executeLongProcess() throws InterruptedException {
		final Promise<String> promise = new Promise<String>();
		
		long startTime = System.currentTimeMillis();

	      long total = 0;
	      
	      for (long i = 0l; i < 10000000000l; i++) {
	         total += i;
	      }
	
	      long stopTime = System.currentTimeMillis();
	      long elapsedTime = stopTime - startTime;
		
		promise.invoke(String.valueOf(elapsedTime));
		return promise;
	}
}
