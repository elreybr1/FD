package com.dex.fd.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dex.fd.dao.jdbc.GenericDAOImpl;
import com.dex.fd.model.Pessoa;
import com.dex.fd.service.exceprion.DBException;

public class PessoaDAO extends GenericDAOImpl<Pessoa>{
	
	private static final String TABLE = "FUNCIONARIO";
	private static final String[] FIELDS = {"ID","NOME","IDADE",
			"DATANASCIMENTO","CPF","DELETA"};	
	

	@Override
	public String getTableName() {
		return TABLE;
	}
	
	@Override
	public Boolean removeById(Integer id) throws DBException {
		String sql = "UPDATE " + TABLE + " SET DELETADA = ? WHERE ID = ?";
		try {
			PreparedStatement ps = super.getPreparedStatement(sql);
			ps.setBoolean(1, true);
			ps.setInt(2, id);
			ps.execute();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		
		return true;
	}
	
	public String getNomeById(Integer idPessoa) throws DBException {
		String nomePessoa = null;
		String sql = "SELECT NOME FROM " + TABLE + " WHERE ID = ?";
		PreparedStatement ps = super.getPreparedStatement(sql);
		try {
			ps.setInt(1, idPessoa);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				nomePessoa = rs.getString("NOME");
			}
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		return nomePessoa;
	}
	
	@Override
	public Pessoa getById(Integer id) throws DBException {
		String sql = "SELECT * FROM pessoa p ";
		sql += "WHERE p.id = ?";
		Pessoa pessoa = null;
		try {
			PreparedStatement ps = super.getPreparedStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				pessoa = popular(rs);
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return pessoa;
	}
	
	/**
	 * @param parteNome
	 * @return
	 */
	public List<Pessoa> getByParteNome(String parteNome) throws DBException {
		List<Pessoa> list = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE + " WHERE NOME LIKE ? AND DELETADA = false";
		
		try {
			PreparedStatement ps = super.getPreparedStatement(sql);
			ps.setString(1, "%"+parteNome+"%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				list.add(this.popular(rs));
			}
		} catch (SQLException ex) {
			throw new DBException(ex);
		}
		
		return list;
	}
 
	@Override
	public List<Pessoa> getAll(String orderBy) throws DBException {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * FROM pessoa p ");
		sql.append("INNER JOIN funcionario f ON p.id_funcionario = f.id ");
		sql.append("WHERE p.DELETADA = false ");
		if (orderBy != null) {
			sql.append("ORDER BY p." + orderBy);
		}
		
		List<Pessoa> lista = new ArrayList<>();
		try {
			ResultSet rs = super.getStatement().executeQuery(sql.toString());
			while (rs.next()) {
				lista.add(popular(rs));
			}
		} catch (SQLException e) {
			throw new DBException(e);
		}
		return lista;
	}
	

	@Override
	public Pessoa popular(ResultSet rs) throws SQLException {

				Pessoa pessoa = new Pessoa();
				
				pessoa.setId(rs.getInt("ID"));
				pessoa.setNomeCompleto(rs.getString("NOME"));
				pessoa.setIdade(rs.getInt("IDADE"));
				pessoa.setDataNascimento(rs.getDate("DATANASCIMENTO").toLocalDate());
				pessoa.setCpf(rs.getString("CPF"));
			
			return pessoa;
		}

	@Override
	public PreparedStatement preparePersistStatement(Pessoa entidade, Boolean isUpdate) throws SQLException {
		PreparedStatement ps = super.getPreparedStatement(super.getInsertClause(FIELDS.length));
		ps.setString(1, entidade.getNomeCompleto());
		ps.setInt(2, entidade.getIdade());
		ps.setDate(3, Date.valueOf(entidade.getDataNascimento()));
		ps.setString(4, entidade.getCpf());
		ps.setInt(5, entidade.getId());

		return ps;
	}

}

