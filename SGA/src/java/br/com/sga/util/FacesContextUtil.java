/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.util;

import javax.faces.context.FacesContext;
import org.hibernate.Session;

/**
 *
 * @author DIGITACAOFUND
 */
public class FacesContextUtil {
    
    public static void setRequestSession(Session session) {
        FacesContext.getCurrentInstance().getExternalContext()
                .getRequestMap().put("session", session);
    }
    
    public static Session getRequestSession() {
        return (Session) FacesContext.getCurrentInstance()
                .getExternalContext().getRequestMap().get("session");
    }
}
