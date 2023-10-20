package com.hodvidar.miscellaneous.livecoding.analysis;
// package com.edeal.recruitment;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.time.Period;


// import com.edeal.frontline.AccessDeniedException;
// import com.edeal.frontline.BasicBean;

public abstract class ProjectGenericBean {

    // 0. I personally prefer all parameters/variables that can be final to be final
    public static <T extends BasicBean> BasicBean saveBean(final HttpSession session,
                                                           final String json,
                                                           final Class<T> type,
                                                           final boolean save) throws Exception {
        // I personally prefer to avoid accumulating indentation
        // Early return or throw improves readability
        if (json == null || json.length() != 0) {
            throw new JSONExceptionEnvelope("Given JSON is null or empty.");
        }

        final JSONObject jsonO;
        try {
            jsonO = new JSONObject(json);
        } catch (final JSONException var8) {
            // 1. Returning a general Exception which is not the best practice.
            // It's better to throw more specific exceptions.
            // Let's use an "Envelope" Exception to wrap the original exception
            throw new JSONExceptionEnvelope(type, var8);
        }

        // 2. The cast (HttpSession) and (String) are redundant
        final BasicBean bean = getBean(session, null, type);

        try {
            bean.populateFromJson(jsonO, save);
            return bean;
        } catch (final AccessDeniedException var7) {
            // 3. Again returning a general Exception which is not the best practice.
            // It's better to throw more specific exceptions.
            // Let's use an "Envelope" Exception to wrap the original exception
            throw new AccessDeniedExceptionEnvelope(type, var7);
        }

    }

    public static <T extends BasicBean> BasicBean getBean(final HttpSession session,
                                                          final String id,
                                                          final Class<T> type)
            throws Exception {
        final boolean isNew = id == null || id.length() == 0;

        try {
            // The cast to BasicBean are redundant
            return isNew ? type.getDeclaredConstructor(HttpSession.class).newInstance(session)
                    : type.getDeclaredConstructor(String.class, HttpSession.class).newInstance(id, session);

        } catch (final Exception var6) {
            // 3. Again returning a general Exception which is not the best practice.
            // It's better to throw more specific exceptions.
            throw new Exception(type.getName(), var6);
        }
    }

    public abstract Period getDuration();

    public abstract String getDurationToString();

    // Issue: We have no indication of what the flag parameter is supposed to be.
    public abstract String getDurationToString(String flag);
}