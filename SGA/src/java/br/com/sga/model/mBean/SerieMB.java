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
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author DIGITACAOFUND
 */
@ManagedBean(name = "serieBean")
@RequestScoped
public class SerieMB implements Serializable {

    private Serie serie = null;
    private List<Serie> series = null;
    private SerieDAOImp serieDAO = null;

    public SerieMB() {
        this.serie = new Serie();
        this.serieDAO = DAOFactory.instance(DAOFactory.HIBERNATE).getSerieDAOImp();
        
        if (this.series == null || this.series.isEmpty()) {
            this.series = serieDAO.getEntityes();
        }
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public List<Serie> getSeries() {
        return this.series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public void atualizarTabela(ActionEvent event) {
        
    }
    
    public void inserirSerie() {
        System.out.println("ENTROU NO MÉTODO inserirSerie()");

        if (serie.getId() == null) {
            serieDAO.save(this.serie);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Série inserida com sucesso.",
                    null));
            setSerie(null);
        } else {
            serieDAO.update(serieDAO.getEntityByID(this.serie.getId()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Série atualizada com sucesso.",
                    null));
            setSerie(null);
        }
        
    }

    public void excluirSerie() {
        serieDAO.remove(serie);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Série excluida com sucesso.",
                null));
        setSerie(new Serie());
    }
}