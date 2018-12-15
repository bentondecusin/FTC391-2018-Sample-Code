/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name="Lamda-Octo")

public class LamdaOcto extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor leftFront = null;
    private DcMotor rightFront = null;
    private DcMotor leftBack = null;
    private DcMotor rightBack = null;
    int p = 3; //default power
    float x = 0;
    float y = 0;

    String direction = "static";
    public String directionSetting(float x, float y){

        if (x >0.4 & y<-0.4/*downright*/){
            return direction = "RB";
        }
        if (x<-0.4 & y<-0.4/*downleft*/){
            return direction = "LB";
        }
        if (x> 0.4 & y> 0.4/*upright*/){
            return direction = "RF";
        }
        if (x<-0.4 & y>0.4/*upleft*/){
            return direction = "LF";
        }

        if (x > 0.4 & y<0.4 & y>-0.4/*right*/){
            return direction = "RR";
        }
        if (x <-0.4 & y <0.4 & y>-0.4 /*left*/){
            return direction = "LL";
        }
        if (x<0.4 & x > -0.4 & y >0.4/*up*/){
            return direction = "FF";
        }
        if (x<0.4 & x>-0.4 & y<-0.4/*down*/){
            return direction ="BB";
        }
        if (y <0.4 & y > -0.4 & x < 0.4 & x > -0.4/*null*/){
            return direction = "static";
        }
        return direction;
    }
    public void move(){

        if (direction == "static"){
            leftFront.setPower(0);
            leftBack.setPower(0);
            rightFront.setPower(0);
            rightBack.setPower(0);
        }
        if (direction == "LF"){
            leftFront.setPower(0);
            leftBack.setPower(p);
            rightFront.setPower(0);
            rightBack.setPower(p);
        }
        if (direction == "RF"){
            leftFront.setPower(p);
            leftBack.setPower(0);
            rightFront.setPower(p);
            rightBack.setPower(0);
        }
        if (direction == "LB"){
            leftFront.setPower(-p);
            leftBack.setPower(0);
            rightFront.setPower(-p);
            rightBack.setPower(0);
        }
        if (direction == "RB"){
            leftFront.setPower(0);
            leftBack.setPower(-p);
            rightFront.setPower(0);
            rightBack.setPower(-p);
        }
        if (direction == "RR"){
            leftFront.setPower(p);
            leftBack.setPower(-p);
            rightFront.setPower(p);
            rightBack.setPower(-p);
        }
        if (direction == "LL"){
            leftFront.setPower(-p);
            leftBack.setPower(p);
            rightFront.setPower(-p);
            rightBack.setPower(p);
        }
        if (direction == "FF"){
            leftFront.setPower(.7*p);
            leftBack.setPower(.7*p);
            rightFront.setPower(.7*p);
            rightBack.setPower(.7*p);}
        if (direction == "BB"){
            leftFront.setPower(-.7*p);
            leftBack.setPower(-.7*p);
            rightFront.setPower(-.7*p);
            rightBack.setPower(-.7*p);
        }
    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        leftFront = hardwareMap.get(DcMotor.class, "mot0");
        leftBack = hardwareMap.get(DcMotor.class, "mot1");
        rightFront = hardwareMap.get(DcMotor.class, "mot2");
        rightBack = hardwareMap.get(DcMotor.class, "mot3");

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            float horizontal = gamepad1.right_stick_x;
            float vertical = - gamepad1.right_stick_y;

            direction = directionSetting(horizontal,vertical);
            move();



        }


        // Show the elapsed game time and wheel power.


        telemetry.update();

    }
}
