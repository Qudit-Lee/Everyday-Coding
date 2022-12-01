package com.project.info.repository

class CitySql {
	
	// select
	public static final String SELECT = """
		SELECT ID, Name, CountryCode, District, Population FROM city WHERE 1=1 
	""";
	
	public static final String COUNTRY_CODE_CONDITION = """
		AND countryCode = :countryCode
	""";
	
	public static final String POPULATION_CONDITION = """
		AND population >= :population
	""";
	
	// insert
	public static final String INSERT = """
		INSERT INTO city (Name, CountryCode, District, Population) values (:name, :countryCode, :district, :population)
	""";

	// update
	public static final String UPDATE = """
		UPDATE city SET Name = :name, CountryCode = :countryCode, District = :district, Population = :population WHERE 1=1  
	""";

	public static final String ID_CONDITION = """
		AND id = :id
	""";
	
	// delete
	public static final String DELETE = """
		DELETE FROM city WHERE 1=1  
	""";
}
