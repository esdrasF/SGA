/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.dao.imp;

import br.com.sga.dao.HibernateDAOImp;
import br.com.sga.model.vo.Pessoa;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author DIGITACAOFUND
 */
public class PessoaDAOImp extends HibernateDAOImp<Pessoa, Integer> {

    public PessoaDAOImp() {
        super();
    }
    
    public Pessoa selectByCPF(Pessoa p) {
        return (Pessoa) getSession().createCriteria(Pessoa.class)
                .add(Restrictions.eq("cpf", p.getCpf())).uniqueResult();
    }
}
