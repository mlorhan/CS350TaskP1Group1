import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sbw.architecture.datatype.*;
import sbw.project.cli.CommandLineInterface;
import sbw.project.cli.action.ActionCreational;
import sbw.project.cli.action.ActionStructural;
import sbw.project.cli.action.ActionBehavioral;
import sbw.project.cli.action.ActionSet;
import sbw.project.cli.action.ActionMiscellaneous;
import sbw.project.cli.action.command.behavioral.CommandDoDeflectRudder;
import sbw.project.cli.action.command.behavioral.CommandDoSelectGear;
import sbw.project.cli.action.command.behavioral.CommandDoSetFlaps;
import sbw.project.cli.action.command.misc.CommandDoExit;
import sbw.project.cli.action.command.misc.CommandDoShowClock;
import sbw.project.cli.action.command.misc.CommandDoWait;

public class CommandParser{

    public ActionSet actionSet;
    public String command;
    public ActionCreational actionCreational;
    public ActionStructural actionStructural;
    public ActionBehavioral actionBehavioral;
    public ActionMiscellaneous actionMiscellaneous;
    
    // create your parser
    // the ActionSet and command string are provided to you automatically.
    // do not do the parsing here
    public CommandParser(ActionSet actionSet, String command){

        this.actionSet = actionSet;
        this.command = command;
        this.actionCreational = actionSet.getActionCreational();
        this.actionStructural = actionSet.getActionStructural();
        this.actionBehavioral = actionSet.getActionBehavioral();
        this.actionMiscellaneous = actionSet.getActionMiscellaneous();

    }

    // do the parsing
    public void parse() throws IOException{
        //split the input by semicolon so multiple commands can be entered with one input
        String[] semiSplit = this.command.split(";", 0);

        //for each command, split it up if there are any comments so I don't have to deal with them anymore
        //also split the command by spaces and figure out what each command is trying to do
        for(int i = 0; i < semiSplit.length; i++){
            String commentSplit = semiSplit[i].split("//", 0)[0];

            //" +" is RegEx that should make it so empty strings are not included in the string array
            String[] commandSplit = commentSplit.split(" +", 0);
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
                createRudder(id, limit, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("ELEVATOR")){
                //CREATE ELEVATOR <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[2]);
                Angle limit = doubleToAngle(Double.parseDouble(commandSplit[5]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[7]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[9]));
                createElevator(id, limit, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("AILERON")){
                //CREATE AILERON <id> WITH LIMIT UP <angle1> DOWN <angle2> SPEED <speed> ACCELERATION <acceleration> 
                Identifier id = stringToIdentifier(commandSplit[2]);
                Angle limitUp = doubleToAngle(Double.parseDouble(commandSplit[6]));
                Angle limitDown = doubleToAngle(Double.parseDouble(commandSplit[8]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[10]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[12]));
                createAileron(id, limitUp, limitDown, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("SPLIT")){
                //CREATE SPLIT FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[3]);
                Angle limit = doubleToAngle(Double.parseDouble(commandSplit[6]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[8]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[10]));
                createSplitFlap(id, limit, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("FOWLER")){
                //CREATE FOWLER FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[3]);
                Angle limit = doubleToAngle(Double.parseDouble(commandSplit[6]));
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[8]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[10]));
                createFowlerFlap(id, limit, speed, acceleration);
            } else if(commandSplit[1].equalsIgnoreCase("ENGINE")){
                //CREATE ENGINE <id> WITH SPEED <speed> ACCELERATION <acceleration>
                Identifier id = stringToIdentifier(commandSplit[2]);
                Speed speed = doubleToSpeed(Double.parseDouble(commandSplit[5]));
                Acceleration acceleration = doubleToAcceleration(Double.parseDouble(commandSplit[7]));
                createEngine(id, speed, acceleration);
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
                //DECLARE RUDDER CONTROLLER <id1> WITH RUDDER <id2>
                Identifier idController = stringToIdentifier(commandSplit[3]);
                Identifier idRudder = stringToIdentifier(commandSplit[6]);
                declareRudderController(idController, idRudder);
            } else if(commandSplit[1].equalsIgnoreCase("ELEVATOR")){
                //DECLARE ELEVATOR CONTROLLER <id1> WITH ELEVATORS <id2> <id3>
                Identifier idController = stringToIdentifier(commandSplit[3]);
                Identifier idElevatorLeft = stringToIdentifier(commandSplit[6]);
                Identifier idElevatorRight = stringToIdentifier(commandSplit[7]);
                declareElevatorController(idController, idElevatorLeft, idElevatorRight);
            } else if(commandSplit[1].equalsIgnoreCase("AILERON")){
                //DECLARE AILERON CONTROLLER <id1> WITH AILERONS <idn>+ PRIMARY <idx> (SLAVE <idslave> TO <idmaster> BY <percent> PERCENT)*
                Identifier idController = stringToIdentifier(commandSplit[3]);
                List<Identifier> idAilerons = new ArrayList<Identifier>();
                int i;
                for(i = 6; !commandSplit[i].equalsIgnoreCase("PRIMARY"); i++){
                    idAilerons.add(stringToIdentifier(commandSplit[i]));
                }
                i += 2;
                Identifier idAileronPrimary = stringToIdentifier(commandSplit[i]);
                i += 2;
                List<AileronSlaveMix> slaveMixes = new ArrayList<AileronSlaveMix>();
                
                for(; i < commandSplit.length; i += 7){
                    if(commandSplit.length > i) { //there is a slavemix section
                        AileronSlaveMix newSlaveMix = stringsToMix(commandSplit[i+2], commandSplit[i], Double.parseDouble(commandSplit[i+4]));
                        slaveMixes.add(newSlaveMix);
                    }
                }
                //declareAileronController(idController, idAilerons, idAileronPrimary, slaveMixes);
            } else if(commandSplit[1].equalsIgnoreCase("FLAP")){
                //DECLARE FLAP CONTROLLER <id> WITH FLAPS <idn>+
                Identifier idController = stringToIdentifier(commandSplit[3]);
                List<Identifier> idFlaps = new ArrayList<Identifier>();
                for(int i = 6; i < commandSplit.length; i++){
                    idFlaps.add(stringToIdentifier(commandSplit[i]));
                }
                declareFlapController(idController, idFlaps);
            } else if(commandSplit[1].equalsIgnoreCase("ENGINE")){
                //DECLARE ENGINE CONTROLLER <id1> WITH ENGINE[S] <idn>+
                Identifier idController = stringToIdentifier(commandSplit[3]);
                List<Identifier> idEngines = new ArrayList<Identifier>();
                for(int i = 6; i < commandSplit.length; i++){
                    idEngines.add(stringToIdentifier(commandSplit[i]));
                }
                //call doDeclareEngineController(idController, idEngines)
            } else if(commandSplit[1].equalsIgnoreCase("GEAR")){
                //DECLARE GEAR CONTROLLER <id1> WITH GEAR NOSE <id2> MAIN <id3> <id4>
                Identifier idController = stringToIdentifier(commandSplit[3]);
                Identifier idNose = stringToIdentifier(commandSplit[7]);
                Identifier idMainLeft = stringToIdentifier(commandSplit[9]);
                Identifier idMainRight = stringToIdentifier(commandSplit[10]);
                declareGearController(idController, idNose, idMainLeft, idMainRight);
            } else if(commandSplit[1].equalsIgnoreCase("BUS")){
                //DECLARE BUS <id1> WITH CONTROLLER[S] <idn>+
                Identifier idBus = stringToIdentifier(commandSplit[2]);
                List<Identifier> idControllers = new ArrayList<Identifier>();
                for(int i = 5; i < commandSplit.length; i++){
                    idControllers.add(stringToIdentifier(commandSplit[i]));
                }
                //call declareBus(idBus, idControllers)
            } else {
                throw new IOException("Invalid DECLARE command input");
            }
        } else if(commandSplit[0].equalsIgnoreCase("COMMIT")){
            //call doCommit()
        } else if(commandSplit[0].equalsIgnoreCase("DO")){ //BEHAVIORAL COMMANDS
            if(commandSplit[3].equalsIgnoreCase("RUDDER")){
            	//DO <id> DEFLECT RUDDER <angle> LEFT|RIGHT
            	Identifier idController = stringToIdentifier(commandSplit[1]);
            	Angle angle = doubleToAngle(Double.parseDouble(commandSplit[4]));
            	boolean isRight = false;
            	if(commandSplit[5].equalsIgnoreCase("Right")) {
            		isRight = true;
            	} else if(commandSplit[5].equalsIgnoreCase("LEFT")) {
            		isRight = false;
            	} else {
            		throw new IOException("Invalid DoDeflectRudder input");
            	}
                deflectRudder(idController, angle, isRight);
            } else if(commandSplit[3].equalsIgnoreCase("ELEVATOR")){
            	//DO <id> DEFLECT ELEVATOR <angle> UP|DOWN
            	Identifier idController = stringToIdentifier(commandSplit[1]);
            	Angle angle = doubleToAngle(Double.parseDouble(commandSplit[4]));
            	boolean isDown = false;
            	if(commandSplit[5].equalsIgnoreCase("DOWN")) {
            		isDown = true;
            	} else if(commandSplit[5].equalsIgnoreCase("UP")) {
            		isDown = false;
            	} else {
            		throw new IOException("Invalid DoDeflectElevator input");
            	}
                deflectElevator(idController, angle, isDown);
            } else if(commandSplit[3].equalsIgnoreCase("AILERONS")){
            	//DO <id> DEFLECT AILERONS <angle> UP|DOWN
            	Identifier idController = stringToIdentifier(commandSplit[1]);
            	Angle angle = doubleToAngle(Double.parseDouble(commandSplit[4]));
            	boolean isDown = false;
            	if(commandSplit[5].equalsIgnoreCase("DOWN")) {
            		isDown = true;
            	} else if(commandSplit[5].equalsIgnoreCase("UP")) {
            		isDown = false;
            	} else {
            		throw new IOException("Invalid DoDeflectAilerons input");
            	}
                //deflectAilerons(idController, angle, isDown);
            } else if(commandSplit[3].equalsIgnoreCase("BRAKE")){
            	//DO <id> SPEED BRAKE ON|OFF
            	Identifier idController = stringToIdentifier(commandSplit[1]);
            	boolean isDeployed = false;
            	if(commandSplit[4].equalsIgnoreCase("ON")) {
            		isDeployed = true;
            	} else if(commandSplit[4].equalsIgnoreCase("OFF")) {
            		isDeployed = false;
            	} else {
            		throw new IOException("Invalid DoDeploySpeedBrake input");
            	}
                //deploySpeedBrake(idController, isDeployed);
            } else if(commandSplit[3].equalsIgnoreCase("FLAP")){
            	//DO <id> DEFLECT FLAP <position>
            	Identifier idController = stringToIdentifier(commandSplit[1]);
            	Position position = intToPosition(Integer.parseInt(commandSplit[4]));
                deflectFlap(idController, position);
            } else if(commandSplit[3].equalsIgnoreCase("POWER")){
                if(commandSplit.length == 5){
                	//DO <id> SET POWER <power>
                	Identifier idController = stringToIdentifier(commandSplit[1]);
                	Power power = doubleToPower(Double.parseDouble(commandSplit[4]));
                    //setEnginePowerAll(idController, power);
                } else if(commandSplit.length == 7){
                	//DO <id1> SET POWER <power> ENGINE <id2>
                	Identifier idController = stringToIdentifier(commandSplit[1]);
                	Power power = doubleToPower(Double.parseDouble(commandSplit[4]));
                	Identifier idEngine = stringToIdentifier(commandSplit[6]);
                    //setEnginePowerSingle(idController, power, idEngine);
                } else {
                    throw new IOException("SET POWER command has incorrect length");
                }
            } else if(commandSplit[2].equalsIgnoreCase("GEAR")){
            	//DO <id> GEAR UP|DOWN
            	Identifier idController = stringToIdentifier(commandSplit[1]);
            	boolean isDown = false;
            	if(commandSplit[3].equalsIgnoreCase("DOWN")) {
            		isDown = true;
            	} else if(commandSplit[3].equalsIgnoreCase("UP")) {
            		isDown = false;
            	} else {
            		throw new IOException("Invalid DoSelectGear input");
            	}
            	gearPosition(idController, isDown);
            } else {
                throw new IOException("Invalid DO command input");
            }

        } else if(commandSplit[0].equalsIgnoreCase("HALT")){
        	//HALT <id>
        	Identifier idController = stringToIdentifier(commandSplit[1]);
            //doHalt(idController);
        } else if(commandSplit[0].charAt(0) == '@'){ //MISCELLANEOUS COMMANDS
            if(commandSplit[0].equalsIgnoreCase("@CLOCK")){
                if(commandSplit.length == 2){
                    boolean isNumber;
                    try {
                        double d = Double.parseDouble(commandSplit[1]);
                    } catch (NumberFormatException nfe){
                        isNumber = false;
                    }
                    isNumber = true;
                    if(isNumber == true) {
                    	//@CLOCK <rate>
                    	Rate rate = intToRate(Integer.parseInt(commandSplit[1]));
                        //setClockRate(rate);
                    } else {
                    	//@CLOCK PAUSE|RESUME|UPDATE
                    	boolean isRunning = false;
                    	if(commandSplit[1].equalsIgnoreCase("PAUSE")) {
                    		isRunning = false;
                    		//setClockRunning(isRunning);
                    	} else if(commandSplit[1].equalsIgnoreCase("RESUME")) {
                    		isRunning = true;
                    		//setClockRunning(isRunning);
                    	} else if(commandSplit[1].equalsIgnoreCase("UPDATE")) {
                    		//doClockUpdate();
                    	} else {
                    		throw new IOException("Invalid @CLOCK input");
                    	}
                    }
                } else if(commandSplit.length == 1){
                	//@CLOCK
                    //showClock();
                } else {
                    throw new IOException("Invalid @CLOCK input length");
                }
            } else if(commandSplit[0].equalsIgnoreCase("@RUN")){
            	//@RUN "<filename>"
            	String fileName = commandSplit[1].substring(1, commandSplit[1].length() - 2);
                //runCommandFile(filename);
            } else if(commandSplit[0].equalsIgnoreCase("@EXIT")){
            	//@EXIT
            	exit();
            } else if(commandSplit[0].equalsIgnoreCase("@WAIT")){
            	//@WAIT <rate>
            	Rate rate = intToRate(Integer.parseInt(commandSplit[1]));
                doWait(rate);
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

    public Position intToPosition(int position){
    	Position.E_Position positionEnum = Position.getEnum(position);
        Position newPosition = new Position(positionEnum);
        return newPosition;
    }

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

    public AileronSlaveMix stringsToMix(String idMaster, String idSlave, double mix){
        Identifier newIdMaster = stringToIdentifier(idMaster);
        Identifier newIdSlave = stringToIdentifier(idSlave);
        Percent newMix = doubleToPercent(mix);
        AileronSlaveMix newSlaveMix = new AileronSlaveMix(newIdMaster, newIdSlave, newMix);
        return newSlaveMix;
    }

    
    /*****CREATIONAL COMMANDS*****/
    
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
    
    //	Input: CREATE SPLIT FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration>
    //	Action: Creates an ActuatorFlapSplit with identifier id that deflects angle degrees down from center (0 degrees) at maximum
    // 		  speed speed and acceleration acceleration.
    // 		  This calls doCreateFlap(), which creates and registers an instance of ActuatorFlapSplit.
    public void createSplitFlap(Identifier id, Angle angle1, Speed speed, Acceleration acceleration) {
    		
    	actionCreational.doCreateFlap(id, false, angle1, speed, acceleration);
    
    }
    
    //	Input:CREATE FOWLER FLAP <id> WITH LIMIT <angle> SPEED <speed> ACCELERATION <acceleration> 
    //	Action: Creates an ActuatorFlapFowler with identifier id that deflects angle degrees down from center (0 degrees) at
    // 		  maximum speed speed and acceleration acceleration. Additional hardcoded definitions are specified in
    //		  ActuatorFlapFowler.
    // 		  This calls doCreateFlap(), which creates and registers an instance of ActuatorFlapFowler.
    public void createFowlerFlap(Identifier id, Angle angle1, Speed speed, Acceleration acceleration) {
    	
    	actionCreational.doCreateFlap(id, true, angle1, speed, acceleration);
    
    }
    
    /*****STRUCTURAL COMMANDS*****/
    
    // Input: DECLARE RUDDER CONTROLLER <id1> WITH RUDDER <id2>
    // Action: Creates a ControllerRudder with identifier id1 containing rudder id2.
    //     This calls doDeclareRudderController(), which creates and registers an instance of ControllerRudder
    public void declareRudderController(Identifier idController, Identifier idRudder){
        actionStructural.doDeclareRudderController(idController, idRudder);
    }

    // Input: DECLARE ELEVATOR CONTROLLER <id1> WITH ELEVATORS <id2> <id3>
    // Action: Creates a ControllerElevator with identifier id1 containing elevators id2 (left) and id3 (right), which must be identical in configuration.
    //     This calls doDeclareElevatorController(), which creates and registers an instance of ControllerElevator.
    public void declareElevatorController(Identifier idController, Identifier idElevatorLeft, Identifier idElevatorRight){
        actionStructural.doDeclareElevatorController(idController, idElevatorLeft, idElevatorRight);
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

    //	Input: DECLARE FLAP CONTROLLER <id> WITH FLAPS <idn>+
    //	Action: Creates a ControllerFlap with identifier id containing flaps idn, where n must be even and the flap types must have
    //		  symmetrically identical configurations.
    //		  This calls doDeclareFlapController(), which creates and registers an instance of ControllerFlap.
    public void declareFlapController(Identifier id1, List<Identifier> idn) {
    	
    	actionStructural.doDeclareFlapController(id1, idn);
    
    }
    
    
    
    // Input: DECLARE ENGINE CONTROLLER <id1> WITH ENGINE[S] <idn>+
    // Action: creates a 'ControllerEngine' with identifier 'id1' containing engines 'idn,' arrayed left to right in order, with identical configurations
    //         the plural form of ENGINE need not correspond grammatically to n
    //         this calls doDeclareEngineController(), which creates and registers an instance of 'ControllerEngine'
    public void declareEngineController(Identifier id1, List<Identifier> idn){



    }
    
    //	Input: DECLARE GEAR CONTROLLER <id1> WITH GEAR NOSE <id2> MAIN <id3> <id4>
    //	Action: Creates a ControllerGear with identifier id1 containing nose gear id2 and main gear id3 (left) and id4 (right). Both
    //		  main gear must be identical in configuration.
    //		  This calls doDeclareGearController(), which creates and registers an instance of ControllerGear.
    public void declareGearController (Identifier id1, Identifier id2, Identifier id3, Identifier id4) {
    	
    	actionStructural.doDeclareGearController(id1, id2, id3, id4);
    
    }
    
    /*****BEHAVIORAL COMMANDS*****/
    
    // Input: DO <id> DEFLECT RUDDER <angle> LEFT|RIGHT
    // Action: Requests that rudder controller id deflect its rudder respectively left or right to angle angle.
    //    	This calls submitCommand() with an instance of CommandDoDeflectRudder.
    public void deflectRudder(Identifier idController, Angle angle, boolean isRight){
    	CommandDoDeflectRudder rudderCommand = new CommandDoDeflectRudder(idController, angle, isRight);
    	this.actionBehavioral.submitCommand(rudderCommand);
    }
    
    // Input: DO <id> DEFLECT ELEVATOR <angle> UP|DOWN
    // Action: Requests that elevator controller id deflect its elevators respectively up or down to angle angle.
    //    	This calls submitCommand() with an instance of CommandDoDeflectElevator
    public void deflectElevator(Identifier idController, Angle angle, boolean isDown){
    	CommandDoDeflectRudder rudderCommand = new CommandDoDeflectRudder(idController, angle, isDown);
    	this.actionBehavioral.submitCommand(rudderCommand);
    }
    
    // Input: DO <id> DEFLECT AILERONS <angle> UP|DOWN
    // Action: requests that aileron controller 'id' deflect its primary aileron respectively up or down to angle 'angle'
    //         the opposite ailerons will deflect in the other direction
    //         the slave ailerons will derive their angles as specified in the slave clause in II.3.a, if one was given; otherwise, the relationship is 100%.
    //         this calls submitCommand() with an instance of 'CommandDoDeflectAilerons'
    public void deflectAileron(Identifier id, Angle angle){



    }

  //	Input: DO <id> DEFLECT FLAP <position>
  //	Action: Requests that flap controller id deflect its flaps to position position, which is correspondingly 0, 25, 50, 75, or 100% of 
  //		    the deflection range. 
  //		    This calls submitCommand() with an instance of CommandDoSetFlaps. 
    public void deflectFlap(Identifier id, Position position) { 
    	CommandDoSetFlaps flapsCommand = new CommandDoSetFlaps(id, position); 
    	
    	this.actionBehavioral.submitCommand(flapsCommand); 
    }
    
    // Input: DO <id> SET POWER <power>
    // Action: requests that engine controller 'id' set the power of all engines to power 'power'
    //         this calls submitCommand() with an instance of 'CommandDoSetEnginePowerAll'
    public void enginePower(Identifier id, Power power){



    }
    
    // Input: DO <id> GEAR UP|DOWN 
    // Action: Requests that gear controller id respectively raise or lower its gear.
    //         This calls submitCommand() with an instance of CommandDoSelectGear
    public void gearPosition (Identifier id, boolean isDown) {    	
    	CommandDoSelectGear gearCommand = new CommandDoSelectGear(id, isDown); 
    	
    	this.actionBehavioral.submitCommand(gearCommand);	
    }
    
    /*****MISCELLANEOUS COMMANDS*****/
    
    // Input: @WAIT <rate>
    // Action: Waits rate milliseconds before executing the next behavioral command. This command is not valid until after @commit.
    //      This calls submitCommand() with an instance of CommandDoWait.
    public void doWait(Rate rate){
    	CommandDoWait waitCommand = new CommandDoWait(rate);
    	this.actionMiscellaneous.submitCommand(waitCommand);
    }
    
    // Input: @CLOCK
    // Action: Outputs the clock rate to the command-line interface as “clock = <rate>”, or “clock = paused” if it is not running.
    //			This calls submitCommand() with an instance of CommandDoShowClock.
    public void showClock() {
    	CommandDoShowClock showClock = new CommandDoShowClock();
    	
    	this.actionMiscellaneous.submitCommand(showClock);
    }
    
    // Input: @EXIT
    // Action: exits the system
    //         this calls submitCommand() with an instance of 'CommandDoExit'
    public void exit(){
        // Create a new exitCommand() to direct the submitCommand()
        CommandDoExit exitCommand = new CommandDoExit();

        // Submit the exit command designed for a miscellaneous action
        this.actionMiscellaneous.submitCommand(exitCommand);
    }

}