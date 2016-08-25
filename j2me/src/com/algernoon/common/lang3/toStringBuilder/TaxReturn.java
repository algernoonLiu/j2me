package com.algernoon.common.lang3.toStringBuilder;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class TaxReturn {
    private String ssn;
    private int year;
    private String lastName;
    private BigDecimal taxableIncome;

    public String getSsn() {
        return ssn;
    }
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public BigDecimal getTaxableIncome() {
        return taxableIncome;
    }
    public void setTaxableIncome(BigDecimal taxableIncome) {
        this.taxableIncome = taxableIncome;
    }

    public TaxReturn() {
    }
    
    public TaxReturn(String pSsn, int pYear, String pLastName, BigDecimal pTaxableIncome) { 
	setSsn(pSsn); 
	setYear(pYear); 
	setLastName(pLastName); 
	setTaxableIncome(pTaxableIncome); 
    }
    
    public String toString() {
	return new ToStringBuilder(this).append("ssn", ssn).append("year", year).append("lastName",lastName).toString();
    }
    
    public int hashCode() {
	return new HashCodeBuilder(3, 7).append(ssn).append(year).toHashCode();
    }
    
    public boolean equals(Object pObject) {
	boolean equals = false;
	if (pObject instanceof TaxReturn) {
	    TaxReturn bean = (TaxReturn) pObject;
	    equals = (new EqualsBuilder().append(ssn, bean.ssn).append(year, bean.year)).isEquals();
	}
	return equals;
    }
    
    public int compareTo(Object pObject) {
	return CompareToBuilder.reflectionCompare(this, pObject);
    }
    
    public static void main(String[] args) {
	TaxReturn return1 = new TaxReturn("012-68-3242", 1998, "O'Brien", new BigDecimal(43000.00));
	TaxReturn return2 = new TaxReturn("012-68-3242", 1999, "O'Brien", new BigDecimal(45000.00));
	TaxReturn return3 = new TaxReturn("012-68-3242", 1999, "O'Brien", new BigDecimal(53222.00));
	System.out.println("ToStringBuilder: " + return1.toString());
	System.out.println("HashCodeBuilder: " + return1.hashCode());
	System.out.println("EqualsBuilder: " + return2.equals(return3));
	System.out.println("CompareToBuilder: " + return2.compareTo(return1));
	
	MyClass one = new MyClass("Becker", 35);
        MyClass two = new MyClass("Becker", 35);
        MyClass three = new MyClass("Agassi", 33);

        System.out.println("One>>>" + one);
        System.out.println("Two>>>" + two);
        System.out.println("Three>>>" + three);

        System.out.println("one equals two? " + one.equals(two));
        System.out.println("one equals three? " + one.equals(three));

        System.out.println("One HashCode>>> " + one.hashCode());
        System.out.println("Two HashCode>>> " + two.hashCode());
        System.out.println("Three HashCode>>> " + three.hashCode());
    }
}

class MyClass {
    private String name = null;
    private int age = 0;

    public MyClass(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this,
            ToStringStyle.MULTI_LINE_STYLE);
    }

    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}

/*ToStringStyle参数说明：
1. DEFAULT_STYLE
   com.entity.Person@182f0db[name=John Doe,age=33,smoker=false]
2. MULTI_LINE_STYLE
    com.entity.Person@182f0db[
   name=John Doe
   age=33
   smoker=false
]
3. NO_FIELD_NAMES_STYLE
   com.entity.Person@182f0db[John Doe,33,false]
4. SHORT_PREFIX_STYLE   （即截去了包名）
  Person[name=John Doe,age=33,smoker=false]
5. SIMPLE_STYLE
   John Doe,33,false*/