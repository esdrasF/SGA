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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author DIGITACAOFUND
 */
@ManagedBean(name = "serieBean")
@RequestScoped
public class SerieMB implements Serializable {
    
    private Serie serie;
    private List<Serie> series;
    
    public SerieMB() {
    }
    
}