package edu.iis.mto.oven;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.inOrder;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class OvenTest {

    @Mock
    private HeatingModule heatingModule;
    @Mock
    private Fan fan;

    private Oven oven;
    private BakingProgram bakingProgram;
    private ArrayList<ProgramStage> programStages= new ArrayList<>();
    private HeatingSettings initHeatingSettings,properHeatingSettings;

    private int initialTemp= 100;
    private int initialTime= 0;
    private int targetTemp= 200;
    private int targetTime= 100;

    OvenTest() {
    }

    @BeforeEach
    void setUp() throws Exception{
        this.oven=new Oven(heatingModule,fan);
        initHeatingSettings = HeatingSettings.builder()
                .withTargetTemp(initialTemp)
                .withTimeInMinutes(initialTime)
                .build();
        properHeatingSettings= HeatingSettings.builder()
                .withTargetTemp(targetTemp)
                .withTimeInMinutes(targetTime)
                .build();
        bakingProgram= BakingProgram.builder().withInitialTemp(100).withStages(programStages).build();
    }

    @Test
    void itCompiles() {
        MatcherAssert.assertThat(true, equalTo(true));
    }

    @Test
    void oneProgramStageHeatingTypeSetToHeat() throws HeatingException {
        programStages.add(ProgramStage.builder().withStageTime(targetTime).withTargetTemp(targetTemp).withHeat(HeatType.HEATER).build());


        oven.start(bakingProgram);
        InOrder callOrder= inOrder(heatingModule,fan);
        callOrder.verify(heatingModule).heater(initHeatingSettings);
        callOrder.verify(fan).isOn();
        callOrder.verify(heatingModule).heater(properHeatingSettings);
    }

    @Test
    void oneProgramStageHeatingTypeSetToGrill() throws HeatingException{
        programStages.add(ProgramStage.builder().withStageTime(targetTime).withTargetTemp(targetTemp).withHeat(HeatType.GRILL).build());

        oven.start(bakingProgram);
        InOrder callOrder= inOrder(heatingModule,fan);
        callOrder.verify(heatingModule).heater(initHeatingSettings);
        callOrder.verify(fan).isOn();
        callOrder.verify(heatingModule).grill(properHeatingSettings);
    }

    @Test
    void oneProgramStageHeatingTypeSetToThermoCirculation() throws HeatingException{
        programStages.add(ProgramStage.builder().withStageTime(targetTime).withTargetTemp(targetTemp).withHeat(HeatType.THERMO_CIRCULATION).build());

        oven.start(bakingProgram);
        InOrder callOrder= inOrder(heatingModule,fan);
        callOrder.verify(heatingModule).heater(initHeatingSettings);
        callOrder.verify(fan).on();
        callOrder.verify(heatingModule).termalCircuit(properHeatingSettings);
        callOrder.verify(fan).off();
    }
}
