package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Autos.MoveOutAuto;
import frc.robot.Autos.OnePieceAuto;
import frc.robot.Autos.TwoPieceAuto;
import frc.robot.commands.AmpPlatformBackwardsCommand;
import frc.robot.commands.AmpPlatformForwardsCommand;
import frc.robot.commands.DriveBoostCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.FloorIntakeINTAKECommand;
import frc.robot.commands.FloorIntakeSPITCommand;
import frc.robot.commands.GryoResetYAWCommand;
import frc.robot.commands.ResetEncodersCommand;
import frc.robot.commands.ShooterAmpCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.ShooterTopIntakeCommand;
import frc.robot.subsystems.Amp;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.FloorIntake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  private Drive m_drive = new Drive();
  private Shooter m_shooter = new Shooter();
  private Amp m_amp = new Amp();
  private FloorIntake m_floorIntake = new FloorIntake();

  

  private final SendableChooser<Integer> autoChooser = new SendableChooser<>();

  private final XboxController m_controller0 = new XboxController(0);
  private final XboxController m_controller1 = new XboxController(1);


  public RobotContainer() {
    // Register Named Commands

    // NamedCommands.registerCommand("ShooterOn", m_shooter.shooterOnCommand());
    // NamedCommands.registerCommand("ShooterOff", m_shooter.shooterOffCommand());
    // NamedCommands.registerCommand("ResetEncoders", new ResetEncodersCommand(m_drive, m_floorIntake, m_shooter));
    // NamedCommands.registerCommand("IntakeOn", new FloorIntakeINTAKECommand(m_floorIntake));
    // NamedCommands.registerCommand("IntakeOff", m_floorIntake.intakeOff());
    // NamedCommands.registerCommand("BrakeModeOn", m_drive.brakeCommand());



    SmartDashboard.putData("Auton", autoChooser);
    autoChooser.addOption("TEST", 1);
    autoChooser.setDefaultOption("One Piece", 2);
    autoChooser.addOption("Move Out Auto", 3);
    autoChooser.addOption("Two Piece", 4);


    configureButtonBindings();
  }

  public void configureButtonBindings() {
    m_drive.setDefaultCommand(
      new DriveCommand(m_drive, () -> -m_controller0.getLeftY(), () -> -m_controller0.getRightX(), () -> m_controller0.getLeftTriggerAxis()>0.8) //Drive/Brake
    );


    new Trigger(() -> m_controller0.getRightTriggerAxis()>0.8).whileTrue(new DriveBoostCommand()); //Boost
    new Trigger(() -> m_controller0.getStartButton()).whileTrue(new InstantCommand(()-> m_drive.resetYaw())); //Reset Yaw
    new Trigger(() -> m_controller0.getStartButton()).whileTrue(new InstantCommand(()-> m_drive.resetOdometry(m_drive.getPose()))); //Reset Encoders


    new Trigger(() -> m_controller1.getBButton()).whileTrue(new FloorIntakeSPITCommand(m_floorIntake)); //Floor Spit
    new Trigger(() -> m_controller1.getXButton()).whileTrue(new FloorIntakeINTAKECommand(m_floorIntake)); //Floor Intake
    new Trigger(() -> m_controller1.getRightTriggerAxis()>0.8).whileTrue(new ShooterCommand(m_shooter)); //Shooter
    new Trigger(() -> m_controller1.getLeftTriggerAxis()>0.8).whileTrue(new ShooterTopIntakeCommand(m_shooter)); //Shooter Top Intake
    new Trigger(() -> m_controller1.getLeftBumper()).whileTrue(new AmpPlatformForwardsCommand(m_amp)); //Amp Platform Forward
    new Trigger(() -> m_controller1.getRightBumper()).whileTrue(new AmpPlatformBackwardsCommand(m_amp)); //Amp Platform Backwards

    new Trigger(() -> m_controller1.getAButton()).whileTrue(new ShooterAmpCommand(m_shooter)); //Amp Shooter
    
    // new Trigger(() -> m_controller1.getRightBumper()).whileTrue(new AmpShooterCommand(m_amp)); //Amp Shooter
    // new Trigger(() -> m_controller1.getLeftBumper()).whileTrue(new AmpIntakeCommand(m_amp)); //Amp Intake
  }

  public Command getAutonomousCommand() {
    switch(autoChooser.getSelected()) {
      case 1: return Commands.print("No Auton For you");
      case 2: return new OnePieceAuto(m_drive, m_floorIntake, m_shooter);
      case 3: return new MoveOutAuto(m_drive);
      case 4: return new TwoPieceAuto(m_drive, m_floorIntake, m_shooter);

      default: return new OnePieceAuto(m_drive, m_floorIntake, m_shooter);

    }

  }
}