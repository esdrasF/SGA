/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.model.mBean;

import br.com.sga.dao.DAOFactory;
import br.com.sga.dao.HibernateDAOImp;
import br.com.sga.dao.imp.AnoLetivoDAOImp;
import br.com.sga.model.vo.AnoLetivo;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author DIGITACAOFUND
 */
@ManagedBean(name = "anoLetivoBean")
@ViewScoped
public class AnoLetivoMB implements Serializable, InterfaceManagedBean<AnoLetivo> {

    private static final long serialVersionUID = 1L;
    
    private AnoLetivo anoLetivo;
    private List<AnoLetivo> listaAnoLetivo;
    private AnoLetivoDAOImp anoLetivoDAOImp;

    public AnoLetivoMB() {
        anoLetivo = new AnoLetivo();
    }

    public AnoLetivo getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(AnoLetivo anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public List<AnoLetivo> getListaAnoLetivo() {
        if (listaAnoLetivo == null || listaAnoLetivo.isEmpty()) {
            return getDAO().getEntityes();
        }
        return listaAnoLetivo;
    }

    public void setListaAnoLetivo(List<AnoLetivo> listaAnoLetivo) {
        this.listaAnoLetivo = listaAnoLetivo;
    }

    public void addAnoLetivo() {
        if (anoLetivo.getId() == null || anoLetivo.getId() == 0) {
            
            if (anoLetivo.getAno().toString().toCharArray().length == 4) {
                inserir();
                recuperarListaAno();
            } else {
                setMessage("Informe um ano válido \"yyyy\"", FacesMessage.SEVERITY_WARN);
            }
            
        } else {
            atualizar();
            recuperarListaAno();
        }
        setAnoLetivo(new AnoLetivo());
    }

    public void removerAnoLetivo() {
        getDAO().remove(anoLetivo);
        setAnoLetivo(new AnoLetivo());
        recuperarListaAno();
        setMessage("Ano removido com sucesso", FacesMessage.SEVERITY_INFO);
    }

    @Override
    public void setMessage(String msg, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, msg, null));
    }

    @Override
    public HibernateDAOImp getDAO() {
        return DAOFactory.instance(DAOFactory.FACTORY_IMPLEMENTATION).getAnoLetivoDAOImp();
    }

    private void recuperarListaAno() {
        setListaAnoLetivo(getDAO().getEntityes());
    }

    private void inserir() {
        getDAO().save(anoLetivo);
        setMessage("Ano inserido com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    private void atualizar() {
        getDAO().update(anoLetivo);
        setMessage("Ano atualizado com sucesso.", FacesMessage.SEVERITY_INFO);
    }
}