package mafoe;

import de.schauderhaft.degraph.configuration.NamedPattern;
import org.junit.Test;

import static de.schauderhaft.degraph.check.Check.classpath;
import static de.schauderhaft.degraph.check.JCheck.violationFree;
import static de.schauderhaft.degraph.check.JLayer.anyOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Simple dependency test with degraph.
 */
public class DependencyTest {

    @Test
    public void testForCycles() {

        assertThat(classpath()
                        .noJars()
                        .including("mafoe.**")
                        .withSlicing("module", "mafoe.(*).**")
                        .withSlicing("web",
                                new NamedPattern("mafoe.freemarker.**", "freemarker"),
                                new NamedPattern("mafoe.web.**", "webmvc"))
                        .withSlicing("persistence",
                                new NamedPattern("mafoe.repository.**", "dao"),
                                new NamedPattern("mafoe.entity.**", "model"))
                        .allow("service", anyOf("dto", "util", "persistence"))
                        .allow("web", "persistence")
                        .allow("config", "remoting"),
                is(violationFree()));
    }
}
