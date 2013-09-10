/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao;

import br.com.sga.dao.imp.AlunoDAOImp;
import br.com.sga.dao.imp.AnoLetivoDAOImp;
import br.com.sga.dao.imp.EnderecoDAOImp;
import br.com.sga.dao.imp.PessoaDAOImp;
import br.com.sga.dao.imp.SerieDAOImp;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DIGITACAOFUND
 */
public class HibernateDAOFactory extends DAOFactory {

    private Logger logger = LoggerFactory.getLogger(HibernateDAOFactory.class);

    @Override
    public AlunoDAOImp getAlunoDAO() {
        return (AlunoDAOImp) instantiateDAO(AlunoDAOImp.class);
    }

    @Override
    public SerieDAOImp getSerieDAOImp() {
        return (SerieDAOImp) instantiateDAO(SerieDAOImp.class);
    }

    @Override
    public EnderecoDAOImp getEnderecoDAOImp() {
        return (EnderecoDAOImp) instantiateDAO(EnderecoDAOImp.class);
    }

    @Override
    public AnoLetivoDAOImp getAnoLetivoDAOImp() {
        return (AnoLetivoDAOImp) instantiateDAO(AnoLetivoDAOImp.class);
    }

    @Override
    public PessoaDAOImp getPessoaDAOImp() {
        return (PessoaDAOImp) instantiateDAO(PessoaDAOImp.class);
    }

    private HibernateDAOImp instantiateDAO(Class daoClass) {
        try {
            logger.debug("Criando instancia de \"" + daoClass.getName() + "\".");
            HibernateDAOImp dao = (HibernateDAOImp) daoClass.newInstance();
            dao.setSession(getCurrentSession());
            logger.debug("Instância criada com sucesso.");
            return dao;
        } catch (Exception e) {
            logger.error("Problemas au tentar criar instância de \"" + daoClass.getName() + "\".", e);
            throw new RuntimeException("Nãom pode instanciar o DAO: " + daoClass, e);
        }
    }

    protected Session getCurrentSession() {
        return (Session) FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get("session");
    }
}
