package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="PhatSwipe; BullRunDepot", group="BPhatSwipe")
public class PhatSwipeBullRunDepot extends BullRunDepot {
    public PhatSwipeBullRunDepot() {
        super(ChassisConfig.forPhatSwipe());
    }
}