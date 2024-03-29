package frc.robot;



//Wpiib imports that are already built in
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

//Rev imports from the REV vendordep
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;



public class Robot extends TimedRobot {
  // easy way to have the CAN ids so they can be changed fast if needed 
  private static final int LeftleadDeviceID = 2;
  private static final int LeftfollowDeviceID = 3;
  private static final int RightleadDeviceID = 4;
  private static final int RightfollowDeviceID = 5;

  //CAN ids for a simple intake/arm
  private static final int IntakeID = 6;
  private static final int ArmID = 7;

  //buttion ids for easy changing
  private static final int IntakeButtionID = 1;
  private static final int OutTakeButtionID = 2;
  private static final int ArmUpButtionID = 3;
  private static final int ArmDownButtionID = 4;



  //declairs your motor controllers aka sparkmaxes 
  private CANSparkMax leftLead;
  private CANSparkMax rightLead;
  private CANSparkMax leftFollow;
  private CANSparkMax rightFollow;
  //declairs a drive system for tank
  private DifferentialDrive RoboDrive;

  //declairs your controller
  private XboxController Joystick;


  //declairs your motor controllers for intake/arm
  private CANSparkMax Intake;
  private CANSparkMax Arm;


  
  @Override
  public void robotInit() {
    //this tells it what the CAN ids are and what type of motor so the neos are a brushless motors
    //Also since this is in robotInit it calls only once and when the robot is turned on 
    leftLead = new CANSparkMax(LeftleadDeviceID, MotorType.kBrushless);
    rightLead = new CANSparkMax(RightleadDeviceID, MotorType.kBrushless);
    leftFollow = new CANSparkMax(LeftfollowDeviceID, MotorType.kBrushless);
    rightFollow = new CANSparkMax(RightfollowDeviceID, MotorType.kBrushless);
    //sets the controller up as well 
    Joystick = new XboxController(0);
    //delcairs the follow motors for tank drive
    leftFollow.follow(leftLead);
    rightFollow.follow(rightLead);
    //finishes setting up tank drive
    RoboDrive = new DifferentialDrive(leftLead, rightLead);

    //tell the reo the CAN ids and motor type
    Intake = new CANSparkMax(IntakeID, MotorType.kBrushless);
    Arm = new CANSparkMax(ArmID, MotorType.kBrushless);
  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() { }

  @Override
  public void teleopPeriodic() {
   //this sets the drive comands so that forwad and back are with the left sticks y movments 
    //then sets the rotation to be the right sticks x movments 
    RoboDrive.arcadeDrive(Joystick.getLeftY(),  Joystick.getRightX());


    //sets a if then statment for one buttion to intake and the other to outtake

    if(Joystick.getRawButton(IntakeButtionID)){//here we use our controller name and we use the .getRawButtion() function pull if the buttion ided is pressed or not
      Intake.set(0.5);//here you use the motor named that was declaired earler and .set() function to make it run the speed that was put in the()
    }else if(Joystick.getRawButton(OutTakeButtionID)){//this else if statment makes it so that if the intake buttions not pressed it checks if the outtake buttions being pressed
      Intake.set(-0.5);//just sets the motor to be in the oposite direction 
    }else{//if both buttions are not being pressed or returning a value of 0 then the motor will not run
      Intake.set(0);
    }


    //set a if then statment for one buttion to lift the arm and the other arm to lower it
    if(Joystick.getRawButton(ArmUpButtionID))//if you only have one command in that if statment you can make it with out{}
      Arm.set(0.6);
    else if(Joystick.getRawButton(ArmDownButtionID))
      Arm.set(-0.6);
    else
      Arm.set(0);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

 

}
