package org.poc.learn.helpers;
import com.amazonaws.services.lambda.runtime.LambdaLogger;


/**
 * Mock LambdaLogger object that prints output to the console
 */
public class MockLambdaConsoleLogger implements LambdaLogger {

    //-------------------------------------------------------------
    // Implementation - LambdaLogger
    //-------------------------------------------------------------

    @Override
    public void log(String s) {
        System.out.println(s);
    }


}