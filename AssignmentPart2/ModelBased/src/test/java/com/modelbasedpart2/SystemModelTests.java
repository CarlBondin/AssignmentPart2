package com.modelbasedpart2;

import com.modelbasedpart2.enums.SystemStatesEnum;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;
import de.scravy.pair.Pair;

import org.junit.Assert;

public class SystemModelTests implements FsmModel{
    Main sut = new Main();
    SystemStatesEnum state = SystemStatesEnum.loggedIn;
    int noOfAlerts = 0;
    int noOfAlertsShown = 0;
    boolean loggedIn = true, alertAdded = false, deleteAlerts = false, accessAlerts = false, postError = false, deleteError = false;

    @Override
    public SystemStatesEnum getState() {
        return state;
    }

    @Override
    public void reset(boolean b) {
        if(b){
            sut = new Main();
        }

        loggedIn = true;
        alertAdded = false;
        deleteAlerts = false;
        accessAlerts = false;
        postError = false;
        deleteError = false;

        state = SystemStatesEnum.loggedIn;
        noOfAlerts = 0;
        noOfAlertsShown = 0;

        System.out.println("Number of Alerts: " + noOfAlerts);
        System.out.println("Number of Alerts Shown: " + noOfAlertsShown);
    }

    public boolean accessAlertsGuard(){
        return getState().equals(SystemStatesEnum.loggedIn) ||
               getState().equals(SystemStatesEnum.alertAdded) ||
               getState().equals(SystemStatesEnum.alertsDeleted) ||
               getState().equals(SystemStatesEnum.postError) ||
               getState().equals(SystemStatesEnum.deleteError);
    }
    public @Action void accessAlerts(){
        Pair <String, Integer> p = sut.accessingAlerts();

        loggedIn = true;
        accessAlerts = true;
        alertAdded = false;
        deleteAlerts = false;
        postError = false;
        deleteError = false;

        state = SystemStatesEnum.myAlerts;
        noOfAlertsShown = p.getSecond();
        System.out.println("Number of Alerts Shown: " + noOfAlertsShown);

        Assert.assertEquals(loggedIn,sut.isLoggedIn());
        Assert.assertEquals(accessAlerts,sut.isAccessAlerts());
        Assert.assertEquals(alertAdded,sut.isAlertAdded());
        Assert.assertEquals(postError,sut.isPostError());
        Assert.assertEquals(deleteError,sut.isDeleteError());
        Assert.assertEquals(deleteAlerts,sut.isDeleteAlerts());
        Assert.assertEquals("https://www.marketalertum.com/Alerts/List",p.getFirst());
    }

    public boolean addAlertGuard(){
        return getState().equals(SystemStatesEnum.myAlerts) ||
                getState().equals(SystemStatesEnum.alertAdded);
    }
    public @Action void addAlert() throws IOException {
        int status = sut.postRequest();

        alertAdded = true;
        accessAlerts = false;

        state = SystemStatesEnum.alertAdded;
        noOfAlerts++;
        if(noOfAlerts <= 5) {
            noOfAlertsShown = noOfAlerts;
        }
        else{
            noOfAlertsShown = 5;
        }

        System.out.println("Number of Alerts: " + noOfAlerts);
        System.out.println("Number of Alerts Shown: " + noOfAlertsShown);

        Assert.assertEquals(accessAlerts,sut.isAccessAlerts());
        Assert.assertEquals(alertAdded,sut.isAlertAdded());
        Assert.assertEquals("A 201 status code indicates that an alert was successfully added", 201, status);
    }

    public boolean badAlertGuard(){
        return getState().equals(SystemStatesEnum.alertAdded);
    }
    public @Action void badAlert(){
        int status = sut.badPostRequest();

        alertAdded = false;
        postError = true;

        state = SystemStatesEnum.postError;

        Assert.assertEquals(alertAdded,sut.isAlertAdded());
        Assert.assertEquals(postError,sut.isPostError());
        Assert.assertEquals("A 400 status code indicates that there was an error when adding an alert",400,status);
    }

    public boolean badDeleteAlertsGuard(){
        return getState().equals(SystemStatesEnum.myAlerts);
    }
    public @Action void badDeleteAlerts(){
        int status = sut.badDeleteRequest();

        deleteError = true;
        accessAlerts = false;

        state = SystemStatesEnum.deleteError;

        Assert.assertEquals(deleteError,sut.isDeleteError());
        Assert.assertEquals(accessAlerts,sut.isAccessAlerts());
        Assert.assertEquals("A 400 status code indicates that there was an error when deleting an alert",400,status);
    }

    public boolean deleteAlertsGuard(){
        return getState().equals(SystemStatesEnum.myAlerts);
    }
    public @Action void deleteAlerts() throws IOException {
        int status = sut.deleteRequest();

        accessAlerts = false;
        deleteAlerts = true;

        state = SystemStatesEnum.alertsDeleted;
        noOfAlerts = 0;
        noOfAlertsShown = 0;

        System.out.println("Number of Alerts: " + noOfAlerts);
        System.out.println("Number of Alerts Shown: " + noOfAlertsShown);

        Assert.assertEquals(accessAlerts,sut.isAccessAlerts());
        Assert.assertEquals(deleteAlerts,sut.isDeleteAlerts());
        Assert.assertEquals("A 200 status code indicates that the alerts were successfully deleted", 200, status);
    }

    @Test
    public void Runner() throws IOException {
        final GreedyTester tester = new GreedyTester(new SystemModelTests());
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        Main m = new Main();
        for(int i=0;i<4;i++) {
            int status = m.systemStatus("46aba3d5-35a9-4850-b5c1-02824284c450");//Re-setting event log
            System.out.println("Reset status: " + status);
            tester.generate(50);
        }
        tester.printCoverage();
    }
}