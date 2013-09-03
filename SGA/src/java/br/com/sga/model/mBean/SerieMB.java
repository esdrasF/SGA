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
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author DIGITACAOFUND
 */
@ManagedBean(name = "serieBean")
@RequestScoped
public class SerieMB implements Serializable {

    private Serie serie;
    private List<Serie> series;
    private SerieDAOImp serieDAO;

    public SerieMB() {
        serie = new Serie();
        serieDAO = DAOFactory.instance(DAOFactory.HIBERNATE).getSerieDAOImp();
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public List<Serie> getSeries() {
        if (this.series == null || this.series.isEmpty()) {
            return serieDAO.getEntityes();
        }
        return this.series;
    }

    public void setSeries(List<Serie> series) {
        this.series = series;
    }

    public void habilitarCamposNovaSerie() {
        setSerie(new Serie());
    }

    public void cancelarNovo() {
        setSerie(new Serie());
    }

    public void limparCampos() {
        setSerie(new Serie());
    }

    public void inserirSerie() {
        System.out.println("ENTROU NO MÉTODO inserirSerie()");
        serieDAO.saveOrUpdate(serie);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Série inserida com sucesso.",
                null));
    }

    public void excluirSerie() {
        serieDAO.remove(serie);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Série excluida com sucesso.",
                null));
        setSerie(new Serie());
    }
}