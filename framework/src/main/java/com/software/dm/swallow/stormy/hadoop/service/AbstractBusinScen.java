package com.software.dm.swallow.stormy.hadoop.service;

public abstract class AbstractBusinScen implements BusinScenInterface {

	protected abstract String[] pv1(String[] lines) throws Exception;

	protected abstract String[] pv2(String[] lines) throws Exception;

	protected abstract String[] coordinates(String[] lines) throws Exception;

	protected abstract String[] uaidhis(String[] lines) throws Exception;

	protected abstract String[] mall(String[] lines) throws Exception;

	protected abstract String[] thematic(String[] lines) throws Exception;

	protected abstract String[] app(String[] lines) throws Exception;

	protected abstract String[] basic(String[] lines) throws Exception;

	protected abstract String[] lacci(String[] lines) throws Exception;

	protected abstract String[] word(String[] lines) throws Exception;
}
