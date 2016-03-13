package ad325.token;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nate on 3/12/16.
 */
public class ProcessCommandLineArgsTests {
    @Test
    public void processArgs_GivenSingleFile_ThenConfigHasFileAndNoOrderAndNoError() {
        new TestScenario()
                .GivenTheArgs("file1.txt")
                .WhenArgsAreProcessed()
                .ThenConfigContainsFiles("file1.txt")
                .ThenOrderByTokenCountDisabled();
    }

    @Test
    public void processArgs_GivenMultipleFiles_ThenConfigHasAllFilesAndNoOrder() {
        new TestScenario()
                .GivenTheArgs("file1.txt", "file2.txt", "file3.txt")
                .WhenArgsAreProcessed()
                .ThenConfigContainsFiles("file1.txt", "file2.txt", "file3.txt")
                .ThenOrderByTokenCountDisabled();
    }

    @Test
    public void processArgs_GivenOSwitch_ThenConfigHasOrderByTokenCount() {
        new TestScenario()
                .GivenTheArgs("-o", "file1.txt")
                .WhenArgsAreProcessed()
                .ThenOrderByTokenCountEnabled();
    }

    @Test
    public void processArgs_GivenFilesAndOrderSwitch_ThenConfigHasFilesAndOrderEnabled() {
        new TestScenario()
                .GivenTheArgs("-o", "file1.txt", "file2.txt")
                .WhenArgsAreProcessed()
                .ThenConfigContainsFiles("file1.txt", "file2.txt")
                .ThenOrderByTokenCountEnabled();
    }

    @Test
    public void processArgs_GivenOrderSwitchIsNotFirst_ThenConfigHasOrderDisabled() {
        new TestScenario()
                .GivenTheArgs("file1.txt", "file2.txt", "-o")
                .WhenArgsAreProcessed()
                .ThenConfigContainsFiles("file1.txt", "file2.txt", "-o")
                .ThenOrderByTokenCountDisabled();
    }

    @Test
    public void processArgs_GivenOrderSwitchAndNoFiles_ThenConfigHasError() {
        new TestScenario()
                .GivenTheArgs("-o")
                .WhenArgsAreProcessed()
                .ThenConfigHasError();
    }

    @Test
    public void processArgs_GivenNoArguments_ThenConfigHasError() {
        new TestScenario()
                .GivenTheArgs()
                .WhenArgsAreProcessed()
                .ThenConfigHasError();
    }

    private class TestScenario {
        private String[] _args;
        private Config _config;

        public TestScenario GivenTheArgs(String... args) {
            _args = args;
            return this;
        }

        public TestScenario WhenArgsAreProcessed() {
            _config = WordCount.processArgs(_args);
            return this;
        }

        public TestScenario ThenConfigContainsFiles(String... files) {
            assertArrayEquals(files, _config.Files);
            return this;
        }

        public TestScenario ThenOrderByTokenCountEnabled() {
            assertTrue(_config.IsReportSortedByCount);
            return this;
        }

        public TestScenario ThenOrderByTokenCountDisabled() {
            assertFalse(_config.IsReportSortedByCount);
            return this;
        }

        public TestScenario ThenConfigHasError() {
            assertTrue(_config.HasError);
            return this;
        }
    }
}
