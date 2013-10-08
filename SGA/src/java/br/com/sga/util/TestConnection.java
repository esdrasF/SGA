/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sga.util;

import br.com.sga.model.vo.Aluno;
import br.com.sga.model.vo.AnoLetivo;
import br.com.sga.model.vo.Endereco;
import br.com.sga.model.vo.Pessoa;
import br.com.sga.model.vo.Serie;
import java.util.Date;
import org.hibernate.Session;


/**
 *
 * @author DIGITACAOFUND
 */
public class TestConnection {

    public static void main(String[] args) {
        
        Session s = HibernateUtil.getSessionFactory().openSession();
        s.beginTransaction();
        
        Aluno a = new Aluno();
        Pessoa pai = new Pessoa();
        Serie serie = new Serie();
        AnoLetivo ano = new AnoLetivo();
        Endereco e = new Endereco();
        
        serie = (Serie) s.get(Serie.class, 247);
        ano = (AnoLetivo) s.get(AnoLetivo.class, 4);
        
        a.setNome("Teste Teste 3");
        a.setDataMatricula(new Date());
        a.setStatus("A");

        pai = (Pessoa) s.get(Pessoa.class, 25);
        pai.setNome("Pai do teste alterado");
        a.getFiliacao().add(pai);
        
        e.setLogradouro("Rua Teste");
        a.setEndereco(e);
        a.setSerie(serie);
        a.setAnoLetivo(ano);
        
        s.save(a);
        s.getTransaction().commit();
    }
}