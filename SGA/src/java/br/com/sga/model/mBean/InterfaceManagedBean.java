/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.model.mBean;

import br.com.sga.dao.HibernateDAOImp;
import javax.faces.application.FacesMessage;

/**
 *
 * @author DIGITACAOFUND
 */
public interface InterfaceManagedBean<T> {
    HibernateDAOImp getDAO();
    void setMessage(String msg, FacesMessage.Severity severity);
}
