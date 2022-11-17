import sbw.architecture.datatype.*;
import sbw.project.cli.CommandLineInterface;
import sbw.project.cli.action.ActionCreational;
import sbw.project.cli.action.ActionSet;
import sbw.project.cli.action.ActionMiscellaneous;
import sbw.project.cli.action.command.misc.CommandDoExit;

public class CommandParser{

    public ActionSet actionSet;
    public String command;

    // create your parser
    // the ActionSet and command string are provided to you automatically.
    // do not do the parsing here
    public CommandParser(ActionSet actionSet, String command){

        this.actionSet = actionSet;
        this.actionCreational = actionSet.getActionCreational();
        this.command = command;

    }

    // do the parsing
    public void parse(){
        //" +" is RegEx that should make it so empty strings are not included in the string array
        String[] commandSplit = getText().split(" +", 0);

        if(commandSplit[0].equals("CREATE")){ //CREATIONAL COMMANDS
            if(commandSplit[1].equals("RUDDER")){
                //call doCreateRudder()
            } else if(commandSplit[1].equals("ELEVATOR")){
                //call doCreateElevator()
            } else if(commandSplit[1].equals("AILERON")){
                //createAileron(id, angle1, angle2, speed, acceleration);
            } else if(commandSplit[1].equals("SPLIT")){
                //call doCreateFlap()
            } else if(commandSplit[1].equals("FOWLER")){
                //call doCreateFlap()
            } else if(commandSplit[1].equals("ENGINE")){
                //call doCreateEngine()
            } else if(commandSplit[1].equals("NOSE")){
                //call doCreateGearNose()
            } else if(commandSplit[1].equals("MAIN")){
                //call doCreateGearMain()
            } else {
                System.out.println("Invalid CREATE command input");
            }

        } else if(commandSplit[0].equals("DECLARE")){ //STRUCTURAL COMMANDS
            if(commandSplit[1].equals("RUDDER")){
                //call doDeclareRudderController()
            } else if(commandSplit[1].equals("ELEVATOR")){
                //call doDeclareElevatorController()
            } else if(commandSplit[1].equals("AILERON")){
                //call doDeclareAileronController()
            } else if(commandSplit[1].equals("FLAP")){
                //call doDeclareFlapController()
            } else if(commandSplit[1].equals("ENGINE")){
                //call doDeclareEngineController()
            } else if(commandSplit[1].equals("GEAR")){
                //call doDeclareGearController()
            } else if(commandSplit[1].equals("BUS")){
                //call doDeclareBus()
            } else {
                System.out.println("Invalid CREATE command input");
            }
        } else if(commandSplit[0].equals("COMMIT")){ //BEHAVIORAL COMMANDS
            //call doCommit()
        } else if(commandSplit[0].equals("DO")){
            if(commandSplit[3].equals("RUDDER")){
                //call submitCommand() with an instance of CommandDoDeflectRudder
            } else if(commandSplit[3].equals("ELEVATOR")){
                //call submitCommand() with an instance of CommandDoDeflectElevator
            } else if(commandSplit[3].equals("AILERONS")){
                //call submitCommand() with an instance of CommandDoDeflectAilerons
            } else if(commandSplit[3].equals("BRAKE")){
                //call submitCommand() with an instance of CommandDoDeploySpeedBrake
            } else if(commandSplit[3].equals("FLAP")){
                //call submitCommand() with an instance of CommandDoSetFlaps
            } else if(commandSplit[3].equals("POWER")){
                if(commandSplit.length == 5){
                    //call submitCommand() with an instance of CommandDoSetEnginePowerAll
                } else if(commandSplit.length == 7){
                    //call submitCommand() with an instance of CommandDoSetEnginePowerSingle
                } else {
                    System.out.println("SET POWER command has incorrect length");
                }
            } else if(commandSplit[2].equals("GEAR")){
                //call submitCommand() with an instance of CommandDoSelectGear
            } else {
                System.out.println("Invalid DO command input");
            }

        } else if(commandSplit[0].equals("HALT")){
            //call submitCommand() with an instance of CommandDoHalt
        } else if(commandSplit[0].charAt(0).equals("@")){ //MISCELLANEOUS COMMANDS
            if(commandSplit[0].equals("@CLOCK")){
                if(commandSplit.length == 2){
                    boolean isNumer;
                    try {
                        double d = Double.parseDouble(commandSplit[1]);
                    } catch (NumberFormatException nfe){
                        isNumber = false;
                    }
                    isNumber = true;
                    if(isNumber == true) {
                        //call submitCommand() with an instance of CommandDoSetClockRate
                    } else {
                        //I may need to split this up even more to account for puase|resume|update
                        //call submitCommand() with an instance of CommandDoSetClockRunning or CommandDoClockUpdate
                    }
                } else if(commandSplit.length == 1){
                    //call submitCommand() with an instance of CommandDoShowClock
                } else {
                    System.out.println("Invalid @CLOCK input length");
                }
            } else if(commandSplit[0].equals("@RUN")){
                //call submitCommand() with an instance of CommandDoRunCommandFile
            } else if(commandSplit[0].equals("@EXIT")){
                //call submitCommand() with an instance of CommandDoExit
            } else if(commandSplit[0].equals("@WAIT")){
                //call submitCommand() with an instance of commandDoWait
            } else {
                System.out.println("Invalid @ command");
            }

        } else {
            System.out.println("Invalid command input");
        }


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
