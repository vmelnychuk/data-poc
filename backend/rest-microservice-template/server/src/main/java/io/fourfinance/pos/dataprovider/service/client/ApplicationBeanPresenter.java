package io.fourfinance.pos.dataprovider.service.client;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.fourfinance.pos.dataprovider.service.client.details.ApplicationBean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class ApplicationBeanPresenter {

    private static final String ENCODING = "UTF-8";
    private static final String TEMPLATES_FOLDER = "/templates";
    private static final String APPLICATION_BEAN_PARAM = "applicationBean";
    private static final String FORMATTER_PARAM = "formatter";
    private static final String COMMON_TEMPLATE = "commonDetails.ftl";

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneOffset.UTC);

    private final Configuration configuration = prepareConfiguration();

    public String toHtmlPresentation(ApplicationBean applicationBean) {
        try {
            Template template = configuration.getTemplate(COMMON_TEMPLATE);
            Writer out = new StringWriter();
            template.process(toMap(applicationBean), out);
            return out.toString();
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
    }

    private Configuration prepareConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        configuration.setDefaultEncoding(ENCODING);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(false);
        configuration.setClassForTemplateLoading(ApplicationBeanPresenter.class, TEMPLATES_FOLDER);
        return configuration;
    }

    private Map<String, Object> toMap(ApplicationBean applicationBean) {
        Map<String, Object> res = new HashMap<>();
        res.put(APPLICATION_BEAN_PARAM, applicationBean);
        res.put(FORMATTER_PARAM, FORMATTER);
        return res;
    }
}
