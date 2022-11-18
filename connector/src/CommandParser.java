import java.io.IOException;

import sbw.architecture.datatype.*;
import sbw.project.cli.CommandLineInterface;
import sbw.project.cli.action.ActionCreational;
import sbw.project.cli.action.ActionSet;
import sbw.project.cli.action.ActionMiscellaneous;
import sbw.project.cli.action.command.misc.CommandDoExit;

public class CommandParser{

    public ActionSet actionSet;
    public String command;
    public ActionCreational actionCreational;

    // create your parser
    // the ActionSet and command string are provided to you automatically.
    // do not do the parsing here
    public CommandParser(ActionSet actionSet, String command){

        this.actionSet = actionSet;
        this.actionCreational = actionSet.getActionCreational();
        this.command = command;

    }

    // do the parsing
    public void parse() throws IOException{
        //split the input by semicolon so multiple commands can be entered with one input
        String[] semiSplit = this.command.split(";", 0);

        //for each command, split the command by spaces and figure out what each command is trying to do
        for(int i = 0; i < semiSplit.length; i++){
            //" +" is RegEx that should make it so empty strings are not included in the string array
            String[] commandSplit = semiSplit[i].split(" +", 0);
            parseHelper(commandSplit);
        }
    }

    public void parseHelper(String[] commandSplit) throws IOException{
        if(commandSplit[0].equalsIgnoreCase("CREATE")){ //CREATIONAL COMMANDS
            if(commandSplit[1].equalsIgnoreCase("RUDDER")){
               	//CREATE RUDDER <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[2]);
                Angle limit = doubleToAngle(Double.parseDouble(commandSplit[5]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[7]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[9]));
                //createRudder(id, limit, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("ELEVATOR")){
                //CREATE ELEVATOR <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[2]);
                Angle limit = doubleToAngle(Double.parseDouble(commandSplit[5]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[7]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[9]));
                //createElevator(id, limit, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("AILERON")){
                //CREATE AILERON <id> WITH LIMIT UP <angle1> DOWN <angle2> SPEED <speed> ACCELERATION <acceleration> 
                Identifier id = stringToIdentifier(commandSplit[2]);
                Angle limitUp = doubleToAngle(Double.parseDouble(commandSplit[6]));
                Angle limitDown = doubleToAngle(Double.parseDouble(commandSplit[8]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[10]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[12]));
                //createAileron(id, limitUp, limitDown, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("SPLIT")){
                //CREATE SPLIT FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[3]);
                Angle limit = doubleToAngle(Double.parseDouble(commandSplit[6]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[8]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[10]));
                //createFlap(id, limit, speed, acceleration)
            } else if(commandSplit[1].equalsIgnoreCase("FOWLER")){
                //CREATE FOWLER FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[3]);
                Angle limit = doubleToAngle(Double.parseDouble(commandSplit[6]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[8]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[10]));
                //createFlap(id, limit, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("ENGINE")){
                //CREATE ENGINE <id> WITH SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[2]);
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[5]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[7]));
                //createEngine(id, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("NOSE")){
                //CREATE NOSE GEAR <id> WITH SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[3]);
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[6]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[8]));
                //createGearNose(id, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("MAIN")){
                //CREATE MAIN GEAR <id> WITH SPEED <speed> ACCELERATION <acceleration> 
                Identifier id = stringToIdentifier(commandSplit[3]);
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[6]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[8]));
                //createGearMain(id, speed, acceleration);
            } else {
                throw new IOException("Invalid CREATE command input");
            }

        } else if(commandSplit[0].equalsIgnoreCase("DECLARE")){ //STRUCTURAL COMMANDS
            if(commandSplit[1].equalsIgnoreCase("RUDDER")){
                //call doDeclareRudderController()
            } else if(commandSplit[1].equalsIgnoreCase("ELEVATOR")){
                //call doDeclareElevatorController()
            } else if(commandSplit[1].equalsIgnoreCase("AILERON")){
                //call doDeclareAileronController()
            } else if(commandSplit[1].equalsIgnoreCase("FLAP")){
                //call doDeclareFlapController()
            } else if(commandSplit[1].equalsIgnoreCase("ENGINE")){
                //call doDeclareEngineController()
            } else if(commandSplit[1].equalsIgnoreCase("GEAR")){
                //call doDeclareGearController()
            } else if(commandSplit[1].equalsIgnoreCase("BUS")){
                //call doDeclareBus()
            } else {
                throw new IOException("Invalid DECLARE command input");
            }
        } else if(commandSplit[0].equalsIgnoreCase("COMMIT")){ //BEHAVIORAL COMMANDS
            //call doCommit()
        } else if(commandSplit[0].equalsIgnoreCase("DO")){
            if(commandSplit[3].equalsIgnoreCase("RUDDER")){
                //call submitCommand() with an instance of CommandDoDeflectRudder
            } else if(commandSplit[3].equalsIgnoreCase("ELEVATOR")){
                //call submitCommand() with an instance of CommandDoDeflectElevator
            } else if(commandSplit[3].equalsIgnoreCase("AILERONS")){
                //call submitCommand() with an instance of CommandDoDeflectAilerons
            } else if(commandSplit[3].equalsIgnoreCase("BRAKE")){
                //call submitCommand() with an instance of CommandDoDeploySpeedBrake
            } else if(commandSplit[3].equalsIgnoreCase("FLAP")){
                //call submitCommand() with an instance of CommandDoSetFlaps
            } else if(commandSplit[3].equalsIgnoreCase("POWER")){
                if(commandSplit.length == 5 || ((commandSplit.length > 5) && (commandSplit[5].startsWith("//")))){
                    //call submitCommand() with an instance of CommandDoSetEnginePowerAll
                } else if(commandSplit.length >= 7){
                    //call submitCommand() with an instance of CommandDoSetEnginePowerSingle
                } else {
                    throw new IOException("SET POWER command has incorrect length");
                }
            } else if(commandSplit[2].equalsIgnoreCase("GEAR")){
                //call submitCommand() with an instance of CommandDoSelectGear
            } else {
                throw new IOException("Invalid DO command input");
            }

        } else if(commandSplit[0].equalsIgnoreCase("HALT")){
            //call submitCommand() with an instance of CommandDoHalt
        } else if(commandSplit[0].charAt(0) == '@'){ //MISCELLANEOUS COMMANDS
            if(commandSplit[0].equalsIgnoreCase("@CLOCK")){
                if(commandSplit.length == 2 || ((commandSplit.length > 2) && (commandSplit[2].startsWith("//")))){
                    boolean isNumber;
                    try {
                        double d = Double.parseDouble(commandSplit[1]);
                    } catch (NumberFormatException nfe){
                        isNumber = false;
                    }
                    isNumber = true;
                    if(isNumber == true) {
                        //call submitCommand() with an instance of CommandDoSetClockRate
                    } else {
                        //I may need to split this up even more to account for pause|resume|update
                        //call submitCommand() with an instance of CommandDoSetClockRunning or CommandDoClockUpdate
                    }
                } else if(commandSplit.length == 1 || ((commandSplit.length > 1) && (commandSplit[1].startsWith("//")))){
                    //call submitCommand() with an instance of CommandDoShowClock
                } else {
                    throw new IOException("Invalid @CLOCK input length");
                }
            } else if(commandSplit[0].equalsIgnoreCase("@RUN")){
                //call submitCommand() with an instance of CommandDoRunCommandFile
            } else if(commandSplit[0].equalsIgnoreCase("@EXIT")){
                //call submitCommand() with an instance of CommandDoExit
            } else if(commandSplit[0].equalsIgnoreCase("@WAIT")){
                //call submitCommand() with an instance of commandDoWait
            } else {
                throw new IOException("Invalid MISCELLANEOUS command");
            }

        } else {
            throw new IOException("Invalid command input");
        }
    }

    public Acceleration doubleToAcceleration(double acceleration){
        Acceleration newAcceleration = new Acceleration(acceleration);
        return newAcceleration;
    }

    public Angle doubleToAngle(double angle){
        Angle newAngle = new Angle(angle);
        return newAngle;
    }
    
    public Identifier stringToIdentifier(String id){
        Identifier newId = new Identifier(id);
        return newId;
    }

    public Percent doubleToPercent(double percent){
        Percent newPercent = new Percent(percent);
        return newPercent;
    }

    //figure out how to use getEnum
    /*
    public Position intToPosition(int position){
        Position newPosition = new Position(getEnum(position));
        return newPosition;
    }
    */

    public Power doubleToPower(double power){
        Power newPower = new Power(power);
        return newPower;
    }

    public Rate intToRate(int rate){
        Rate newRate = new Rate(rate);
        return newRate;
    }

    public Speed doubleToSpeed(double speed){
        Speed newSpeed = new Speed(speed);
        return newSpeed;
    }

    // Input: CREATE RUDDER <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
    // Action: Creates an ActuatorRudder with identifier id that deflects angle degrees left (negative) or right (positive) from neutral (0 degrees) at maximum speed speed and acceleration acceleration.
    //         This calls doCreateRudder(), which creates and registers an instance of ActuatorRudder.
    public void createRudder(Identifier id, Angle angle, Speed speed, Acceleration acceleration){

        actionCreational.doCreateRudder(id, angle, speed, acceleration);

    }

    // Input: CREATE ELEVATOR <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration> 
    // Action: Creates an ActuatorElevator with identifier id that deflects angle degrees up (positive) or down (negative) from neutral (0 degrees) at maximum speed speed and acceleration acceleration.
    //         This calls doCreateElevator(), which creates and registers an instance of ActuatorElevator.
    public void createElevator(Identifier id, Angle angle, Speed speed, Acceleration acceleration){

        actionCreational.doCreateElevator(id, angle, speed, acceleration);

    }

    // Input: CREATE AILERON <id> WITH LIMIT UP <angle1> DOWN <angle2> SPEED <speed> ACCELERATION <acceleration>
    // Action: creates an 'ActuatorAileron' with identifier 'id' that deflects 'angle1' degrees up (positive) from neutral (0 degrees) and 'angle2' degrees down (negative) at maximum speed 'speed' and acceleration 'acceleration'
    //         this calls doCreateAileron(), which creates and registers an instance of 'ActuatorAileron'
    public void createAileron(Identifier id, Angle angle1, Angle angle2, Speed speed, Acceleration acceleration){

        actionCreational.doCreateAileron(id, angle1, angle2, speed, acceleration);

    }

    // Input: CREATE ENGINE <id> WITH SPEED <speed> ACCELERATION <acceleration>
    // Action: creates an 'ActuatorEngine' with identifier 'id' with maximum speed 'speed' and acceleration 'acceleration'
    //         the interval is always [0..100] percent power
    //         this calls doCreateEngine(), which creates and registers an instance of 'ActuatorEngine'
    public void createEngine(Identifier id, Speed speed, Acceleration acceleration){

        actionCreational.doCreateEngine(id, speed, acceleration);

    }

    //Input: CREATE SPLIT FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
    //Action: Creates an ActuatorFlapSplit with identifier id that deflects angle degrees down from center (0 degrees) at maximum
    // 		  speed speed and acceleration acceleration.
    // 		  This calls doCreateFlap(), which creates and registers an instance of ActuatorFlapSplit.
    public void createSplitFlap(Identifier id, Angle angle1, Speed speed, Acceleration acceleration) {
    	actionCreational.doCreateFlap(id, false, angle1, speed, acceleration);
    }
    
    //Input:CREATE FOWLER FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration> 
    //Action: Creates an ActuatorFlapFowler with identifier id that deflects angle degrees down from center (0 degrees) at
    // 		  maximum speed speed and acceleration acceleration. Additional hardcoded definitions are specified in
    //		  ActuatorFlapFowler.
    // 		  This calls doCreateFlap(), which creates and registers an instance of ActuatorFlapFowler.
    public void createFowlerFlap(Identifier id, Angle angle1, Speed speed, Acceleration acceleration) {
    	actionCreational.doCreateAileron(id, true, angle1, speed, acceleration);
    }

    // Input: DECLARE AILERON CONTROLLER <id1> WITH AILERONS <idn>+ PRIMARY <idx> (SLAVE <idslave> TO <idmaster> BY <percent> PERCENT)*
    // Action: creates a 'ControllerAileron' with identifier 'id1' containing n ailerons 'idn,' where n is even
    //         the first half of n in order are on the left wing, and the second half on the right
    //         'idx' specifies which of 'idn' is the primary one that is directly commanded by a request to this controller
    //         it must be on the left wing
    //         the others respond based on it: all ailerons on the same side deflect in the same direction, and all on the opposite side deflect in the opposite direction (except when used as a speed brake; see III.3.b).
    //         'idslave' optionally bases its deflection (and likewise its opposite's) on 'idmaster' by percent 'percent'
    //         any chain of mixing is possible, but there are no cyclical relationships
    //         aileron configurations must be symmetrically identical
    //         this calls two variants of doDeclareAileronController(), which creates and registers an instance of 'ControllerAileron'
    public void declareAileronController(Identifier id1, Identifier idn, Identifier idx, Identifier idslave, Identifier idmaster, Percent percent){



    }

    // Input: DECLARE ENGINE CONTROLLER <id1> WITH ENGINE[S] <idn>+
    // Action: creates a 'ControllerEngine' with identifier 'id1' containing engines 'idn,' arrayed left to right in order, with identical configurations
    //         the plural form of ENGINE need not correspond grammatically to n
    //         this calls doDeclareEngineController(), which creates and registers an instance of 'ControllerEngine'
    public void declareEngineController(Identifier id1, Identifier idn){



    }

    // Input: DO <id> DEFLECT AILERONS <angle> UP|DOWN
    // Action: requests that aileron controller 'id' deflect its primary aileron respectively up or down to angle 'angle'
    //         the opposite ailerons will deflect in the other direction
    //         the slave ailerons will derive their angles as specified in the slave clause in II.3.a, if one was given; otherwise, the relationship is 100%.
    //         this calls submitCommand() with an instance of 'CommandDoDeflectAilerons'
    public void deflectAileron(Identifier id, Angle angle){



    }

    // Input: DO <id> SET POWER <power>
    // Action: requests that engine controller 'id' set the power of all engines to power 'power'
    //         this calls submitCommand() with an instance of 'CommandDoSetEnginePowerAll'
    public void enginePower(Identifier id, Power power){



    }

    // Input: @EXIT
    // Action: exits the system
    //         this calls submitCommand() with an instance of 'CommandDoExit'
    public void exit(){

        // Create a new CommandLineInterface to construct an ActionSet
        CommandLineInterface cli = new CommandLineInterface();
        // Create a new ActionSet to access the protected ActionMiscellaneous constructor
        ActionSet actions = new ActionSet(cli);
        // Create a new ActionMiscellaneous to access the protected submitCommand()
        ActionMiscellaneous action = actions.getActionMiscellaneous();
        // Create a new exitCommand() to direct the submitCommand()
        CommandDoExit exitCommand = new CommandDoExit();

        // Submit the exit command designed for a miscellaneous action
        action.submitCommand(exitCommand);


    }

}
