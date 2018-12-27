/*
 * Author: Benton Li '19
 * Version: 1.0
 *
 * */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;


@Autonomous(name = "Far-1")

public class BetaFar extends LinearOpMode {
    //preparation for these cool vuforia stuffs
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";
    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;
    private static final String VUFORIA_KEY = "AUrb6t//////AAABmZ7sUnVME0wvu2pmOKRP5ilgE5gzg4vWVqHNhc0ef2FEwf9NlosWkTS81UmRvZ0UTHFjPeQYLKL6iY60ZJQcJFcMftURUv/1nA/9YELScRwzltxrUAFpfMA/VE9VTaNPTQQYUfm1Z1wUwY6fAJBwDvZJP+UBqPD0AJxz0Gf8jgcdCVgu4A7VtVdk1PRMTSUkHdOEm+VmXzpjxL9X4d/v81mx3aqJbVc6+qhUD53umiep/wCgl9WxHYY6ZEM2tuS7Eih3TexL24HLFvdEu79t24yTzCFz6du/hB12nfyySO78UWbdlusHuHIv0ZI5/IWh4RigF057FaLWc4F+EluGBkO0c6ygIaciN5fHPS9l7dtj";
    String goldLocation;

    //configure motors

    private DcMotor left = null;
    private DcMotor right = null;
    private DcMotor lift = null;
    private Servo launch = null;


    ElapsedTime runTime = new ElapsedTime();
    double checkpoint1 = 10;



    public void sample() {
        runTime.reset();
        initVuforia();
        initTfod();
        tfod.activate();

        goldLocation = "N";
        while (goldLocation == "N" && runTime.time() < checkpoint1) {
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    if (updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getLeft();
                            } else if (silverMineral1X == -1) {
                                silverMineral1X = (int) recognition.getLeft();
                            } else {
                                silverMineral2X = (int) recognition.getLeft();
                            }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Left");
                                goldLocation = "L";
                                break;

                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                goldLocation = "R";
                                break;

                            } else {
                                telemetry.addData("Gold Mineral Position", "Center");
                                goldLocation = "C";
                                break;
                            }
                        }
                    }
                    telemetry.update();
                }
            }
        }
        //Four endings for this story: left, right, center, nothing


    }

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }


    private void configureMotors() {

        left = hardwareMap.get(DcMotor.class, "mot0");
         right = hardwareMap.get(DcMotor.class, "mot1");
         lift = hardwareMap.get(DcMotor.class,"mot2");
         launch = hardwareMap.get(Servo.class,"ser");
        //set rotational direction

         left.setDirection(DcMotor.Direction.FORWARD);
         right.setDirection(DcMotor.Direction.REVERSE);
         lift.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();
    }

    public void linearForward(double dX){//dx means displacement
        runTime.reset();
        left.setPower(.5);//power depends on the the robot and case studies are needed
        right.setPower(.5);
        while (runTime.seconds()<dX){
            telemetry.addData("time",runTime.seconds());
            telemetry.update();
        }
        left.setPower(0);
        right.setPower(0);
    }
    public void linearBackward(double dX){//dx means displacement
        runTime.reset();
        left.setPower(-.5);//power depends on the the robot and case studies are needed
        right.setPower(-.5);
        while (runTime.seconds()<dX){
            telemetry.addData("time",runTime.seconds());
            telemetry.update();
        }
        left.setPower(0);
        right.setPower(0);
    }
    public void angularClockwise(double dA){//dA means change in angular degree
        runTime.reset();;
        left.setPower(.5);
        right.setPower(-.5);//power depends on the the robot and case studies are needed
        while (runTime.seconds()<dA){
            telemetry.addData("time",runTime.seconds());
            telemetry.update();
        }
        left.setPower(0);
        right.setPower(0);
    }
    public void angularCounterClockwise(double dA){//dA means change in angular degree
        runTime.reset();;
        left.setPower(-.5);
        right.setPower(.5);//power depends on the the robot and case studies are needed
        while (runTime.seconds()<dA){
            telemetry.addData("time",runTime.seconds());
            telemetry.update();
        }
        left.setPower(0);
        right.setPower(0);
    }

    private void pitch() {//pitch the ball means game starts. lower down, leave the latch, come up right in front of the mineral

    }

    private void bat(String location) {

        if (location == "L") {
            linearForward(5);
            angularCounterClockwise(45);
            linearForward(10);
            linearBackward(10);
            angularCounterClockwise(45);

        }
        if (location == "R") {
            linearForward(5);
            angularClockwise(45);
            linearForward(10);
            linearBackward(10);
            angularCounterClockwise(135);

        }
        if (location == "C" ||location == "N") {
            linearForward(10);//in
            linearBackward(10);//out
            angularCounterClockwise(90);



        }
    }

    private void homeRun() {

        linearForward(21);
        angularCounterClockwise(45);
        linearForward(20);
        launch.setPosition(20);
    }


    @Override
    public void runOpMode() {
        configureMotors();
        sample();
        bat(goldLocation);
        homeRun();

        }

    }



