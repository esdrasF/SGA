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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DAOFactory {
    
    private static Logger logger = LoggerFactory.getLogger(DAOFactory.class);

    public static final Class FACTORY_IMPLEMENTATION = br.com.sga.dao.HibernateDAOFactory.class;

    public static DAOFactory instance(Class factoryImplementation) {
        logger.debug("Fabrica da DAO (DAOFactory iniciada.)");
        try {
            return (DAOFactory) factoryImplementation.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criar o DAOFactory." + e);
        }
    }

    public abstract AlunoDAOImp getAlunoDAO();
    public abstract SerieDAOImp getSerieDAOImp();
    public abstract EnderecoDAOImp getEnderecoDAOImp();
    public abstract AnoLetivoDAOImp getAnoLetivoDAOImp();
    public abstract PessoaDAOImp getPessoaDAOImp();
    
}
