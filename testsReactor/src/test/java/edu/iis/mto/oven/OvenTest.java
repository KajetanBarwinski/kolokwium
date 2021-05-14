package edu.iis.mto.oven;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OvenTest {

    @Mock
    private HeatingModule heatingModule;
    @Mock
    private Fan fan;

    private Oven oven;

    @BeforeEach
    void setUp() throws Exception{
        this.oven=new Oven(heatingModule,fan);
    }

    @Test
    void itCompiles() {
        MatcherAssert.assertThat(true, equalTo(true));
    }

}
