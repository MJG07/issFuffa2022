package it.unibo.SonarObservable.Utils;

public interface IApplication {
	public void doJob( String domainConfig, String systemConfig );
	public String getName();
}
