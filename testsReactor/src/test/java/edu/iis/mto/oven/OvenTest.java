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
    private HeatingSettings initHeatingSettings;

    @BeforeEach
    void setUp() throws Exception{
        this.oven=new Oven(heatingModule,fan);
        initHeatingSettings = HeatingSettings.builder()
                .withTargetTemp(100)
                .withTimeInMinutes(0)
                .build();
    }

    @Test
    void itCompiles() {
        MatcherAssert.assertThat(true, equalTo(true));
    }

    @Test
    void oneProgramStageHeatingTypeSetToHeat() throws HeatingException {
        programStages.add(ProgramStage.builder().withStageTime(100).withTargetTemp(200).withHeat(HeatType.HEATER).build());
        bakingProgram= BakingProgram.builder().withInitialTemp(100).withStages(programStages).build();
        HeatingSettings properHeatingSettings= HeatingSettings.builder()
                .withTargetTemp(200)
                .withTimeInMinutes(100)
                .build();


        oven.start(bakingProgram);
        InOrder callOrder= inOrder(heatingModule,fan);
        callOrder.verify(heatingModule).heater(initHeatingSettings);
        callOrder.verify(fan).isOn();
        callOrder.verify(heatingModule).heater(properHeatingSettings);
    }
}
