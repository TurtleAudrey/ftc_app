package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.MagneticFlux;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Temperature;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.FRONT;


/**
 * This just runs from the position closest to the crater, into the crater.
 */
public abstract class DelaySampleDescendMarkerDepot extends StandardChassis {

    private boolean madeTheRun = false;

    public DelaySampleDescendMarkerDepot(ChassisConfig config) {
        super(config);
    }

    /**
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        initMotors();
        initTimeouts();
        initSampling();
    }


    /**
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop () {

    }

    /**
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start () {
        // Reset the game timer.
        runtime.reset();
    }

    /**
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop () {
        stopSampling();
    }

    /**
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop () {

        if (madeTheRun == false) {
            sleep(1000);
            descendFromLander();

            //When gold is detected on the side of the screen it is on, strafe left, right or stay depending on where it is. Then, move forward into the crater.\
            GoldStatus pos = sampleProbe();

            // we will always have a valid pos here.
            encoderDrive(15);
            if (pos == GoldStatus.Left) {
                turnLeft(90);
                encoderDrive(10);
                turnRight(75);
                encoderDrive(20);
                turnRight(90);
                dropFlag();
                sleep(3000);
                resetFlag();
                turnRight(125);
                encoderDrive(90, 90);

            } else if (pos == GoldStatus.Right) {
                turnRight(90);
                encoderDrive(10);
                turnLeft(75);
                encoderDrive(20);
                turnLeft(90);
                dropFlag();
                sleep(3000);
                resetFlag();
                turnRight(125);
                encoderDrive(90, 90);

            } else {
                encoderDrive(30);
                dropFlag();
                sleep(3000);
                resetFlag();
                turnRight(125);
                encoderDrive(90, 90);

            }
            madeTheRun = true;
        }

        // Show the elapsed game time and wheel power.
        telemetry.addData("Status", "time: " + runtime.toString());
        telemetry.addData("Status", "madeTheRun=%b", madeTheRun);
    }
}

