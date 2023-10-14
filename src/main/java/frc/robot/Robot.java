// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {
  private final CANSparkMax m_leftFront = new CANSparkMax(4, MotorType.kBrushless);
  private final CANSparkMax m_leftBack = new CANSparkMax(3, MotorType.kBrushless);
  private final CANSparkMax m_rightFront = new CANSparkMax(1, MotorType.kBrushless);
  private final CANSparkMax m_rightBack = new CANSparkMax(2, MotorType.kBrushless);
  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_leftFront, m_rightFront);
  private final XboxController m_controller = new XboxController(0);
  private final Timer m_timer = new Timer();

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightFront.setInverted(true);
    m_leftFront.setInverted(false);

    //m_rightBack.follow(m_rightFront);
    //m_leftBack.follow(m_leftFront);
  }

  /** This function is run once each time the robot enters autonomous mode. */
  @Override
  public void autonomousInit() {
    m_timer.restart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    // Drive for 2 seconds
    if (m_timer.get() < 2.0) {
      // Drive forwards half speed, make sure to turn input squaring off
      m_robotDrive.arcadeDrive(0.5, 0.0, false);
    } else {
      m_robotDrive.stopMotor(); // stop robot
    }
  }

  /** This function is called once each time the robot enters teleoperated mode. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during teleoperated mode. */
  @Override
  public void teleopPeriodic() {
    double xSpeed = m_controller.getLeftY();
      if (xSpeed < 0.1 && xSpeed > -0.1) {
        xSpeed = 0;
    }
    double xRotation = m_controller.getRightX();
      if (xRotation < 0.1 && xRotation > -0.1) {
        xRotation = 0;
    }
    m_robotDrive.arcadeDrive(-xSpeed, -xRotation);
    //m_robotDrive.curvatureDrive(-m_controller.getLeftX(), -m_controller.getRightX(), false);
    //m_leftBack.set(xSpeed);

  }

  /** This function is called once each time the robot enters test mode. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
