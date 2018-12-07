
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
 dick.setPower(-0.75);//lefting mech extending;robot lowering down
}
if(runTime.time() > 14 && runTime.time() < 16){
        dick.setPower(0);//lifting mech stops
        drive[1].setPower(5);
        drive[0].setPower(5);//counter clockwise turning
}
if(runTime.time() > 16 && runTime.time() < 17)
{
dick.setPower(0);
drive[1].setPower(5);
drive[0].setPower(-5);//move forward
}
if(runTime.time() > 17 && runTime.time() < 18){

drive[1].setPower(-5);
drive[0].setPower(-5);// clockwise turning; direction adjusting 
}
if(runTime.time() > 18 && runTime.time() < 23)
{
dick.setPower(0.75);//lifting mech retracts
drive[1].setPower(0);
drive[0].setPower(0);
}
if(runTime.time() > 23){
  dick.setPower(0);//lifting mech stops
}


       
    }
}
