package mafoe.util;

import mafoe.service.DemoService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RemotingHelperTest {

    @Test
    public void serviceInterfaceToEndpoint() {
        String name = RemotingHelper.serviceInterfaceToEndpoint(DemoService.class);
        assertEquals("/DemoService", name);
    }
}