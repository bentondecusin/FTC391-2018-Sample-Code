package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Benton Auto")

public class BentonAuto extends OpMode{
    private DcMotor[] drive = new DcMotor[2];
    private DcMotor dick = null;
    private int counter = 0;
    
    @Override
    public void init() {
        drive[0] = hardwareMap.get(DcMotor.class, "mot0");
        drive[1] = hardwareMap.get(DcMotor.class, "mot1");
        dick = hardwareMap.get(DcMotor.class, "mot2");
        drive[0].setDirection(DcMotor.Direction.FORWARD);
        drive[1].setDirection(DcMotor.Direction.FORWARD);
        dick.setDirection(DcMotor.Direction.REVERSE);
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void init_loop() {
        
    }

    @Override
    public void start() {
        
    }
    public ElapsedTime runTime = new ElapsedTime();


    @Override
    public void loop() {
if(runTime.time() < 14 )
{
 dick.setPower(-0.75);
}
if(runTime.time() > 14 && runTime.time() < 17){
        dick.setPower(0);
        drive[1].setPower(5);
}
if(runTime.time() > 17 && runTime.time() < 32)
{
 dick.setPower(0.75);
}
if(runTime.time() > 32){
  dick.setPower(0);
}


       
    }
}