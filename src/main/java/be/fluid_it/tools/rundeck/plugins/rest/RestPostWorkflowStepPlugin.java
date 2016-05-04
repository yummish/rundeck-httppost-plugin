package be.fluid_it.tools.rundeck.plugins.rest;

import java.util.Map;

import org.boon.HTTP;

import com.dtolabs.rundeck.core.execution.workflow.steps.StepException;
import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.plugins.ServiceNameConstants;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import com.dtolabs.rundeck.plugins.step.PluginStepContext;
import com.dtolabs.rundeck.plugins.step.StepPlugin;

import be.fluid_it.tools.rundeck.plugins.util.ExpandUtil;

@Plugin(name = RestPostWorkflowStepPlugin.SERVICE_PROVIDER_NAME, service = ServiceNameConstants.WorkflowStep)
@PluginDescription(title = "Rest Post Plugin", description = "Performs a post to a rest resource")
public class RestPostWorkflowStepPlugin implements StepPlugin {
    public static final String SERVICE_PROVIDER_NAME = "eu.europa.ec.ercea.backoffice.tools.rundeck.plugins.rest.RestPostWorkflowStepPlugin";

    @PluginProperty(title = "Remote URL", description = "Rest resource url to be posted to", required = true)
    private String remoteURL;

    @PluginProperty(title = "Post parameters", description = "Post parameters in JSON format: {...}", required = false)
    private String postParameters;

    @Override
    public void executeStep(PluginStepContext context, Map<String, Object> configuration) throws StepException {
        Map<String, Map<String, String>> dataContext = context.getExecutionContext().getDataContext();

        String expandedRemoteUrl = ExpandUtil.expand(remoteURL, context.getExecutionContext());

        StringBuffer buffer = new StringBuffer("Posting to [");
        buffer.append(expandedRemoteUrl).append("]");
        if (postParameters != null) {
            buffer.append(" with parameters [").append(postParameters).append("]");
        }
        buffer.append(" ...");
        System.out.println(buffer);

        String result = HTTP.postJSON(expandedRemoteUrl, postParameters);

        System.out.println(result);
    }
}