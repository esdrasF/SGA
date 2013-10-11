/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.model.mBean;

import br.com.sga.dao.DAOFactory;
import br.com.sga.dao.imp.SerieDAOImp;
import br.com.sga.model.vo.Serie;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author DIGITACAOFUND
 */

@ManagedBean(name = "serieBean")
@SessionScoped
public class SerieMB implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Serie serie;
    private List<Serie> series;

    public SerieMB() {
        serie = new Serie();
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public List<Serie> getSeries() {
        if (series == null || series.size() == 0) {
            recuperaSeries();
        }
        return series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public void addSerie() {
        if (serie.getId() == null || serie.getId() == 0) {
            inserirSerie();
            setSerie(new Serie());
            recuperaSeries();
        } else {
            atualizarSerie();
            recuperaSeries();
        }
        limparSerie();
    }

    public void removerSerie() {
        getSerieDAO().remove(serie);
        setSerie(new Serie());
        setSeries(null);
        setMensagem("Serie removida com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    private SerieDAOImp getSerieDAO() {
        return DAOFactory.instance(DAOFactory.FACTORY_IMPLEMENTATION).getSerieDAOImp();
    }

    private void inserirSerie() {
        getSerieDAO().save(serie);
        setMensagem("Serie inserida com sucesso.", FacesMessage.SEVERITY_INFO);
    }

    private void atualizarSerie() {
        getSerieDAO().update(serie);
        setMensagem("Serie atualizada com sucesso.", FacesMessage.SEVERITY_INFO);

    }

    private void limparSerie() {
        setSerie(new Serie());
    }

    private void setMensagem(String msg, FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage(severity, msg, null));
    }

    private void recuperaSeries() {
        setSeries(getSerieDAO().getEntityes());
    }
}