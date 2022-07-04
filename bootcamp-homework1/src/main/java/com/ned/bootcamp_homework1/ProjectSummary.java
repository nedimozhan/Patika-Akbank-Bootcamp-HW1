package com.ned.bootcamp_homework1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.maven.artifact.repository.metadata.Plugin;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Developer;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "summarize", defaultPhase = LifecyclePhase.INSTALL)
public class ProjectSummary extends AbstractMojo{
	
	@Parameter(defaultValue = "${project}", required = true)
	private MavenProject project;
	
	@Parameter(property = "outputFile",defaultValue = "defaultSummarize", required = true)
	private String outputFile;
	
	private FileWriter f0;
	private String newLine;
	
	private final String release = "release.date";
	
	public void execute() throws MojoExecutionException, MojoFailureException {
		// TODO Auto-generated method stub
		
		String pathTemp = System.getProperty("user.dir"); //it will give the root directory of where your program runs
		pathTemp = pathTemp + "/target/" + this.outputFile + ".txt"; //here file name from the argument
		
		try {
			
			//Create a txt file 
			f0 = new FileWriter(pathTemp);
			newLine = System.getProperty("line.separator");
			
			// Write Project Info
			f0.write("Project info : ");
			f0.write(project.getGroupId());
			f0.write(".");
			f0.write(project.getArtifactId());
			f0.write(".");
			f0.write(project.getVersion());
			
			f0.write(newLine);
			
			// Write Developers
			f0.write("Developers : ");
			for(int i=0;i<project.getDevelopers().size();i++) {
				Developer developer = (Developer)project.getDevelopers().get(i);
				String tempString = " Developer " + String.valueOf((i+1)) + " Name : " + developer.getName();
				f0.write(tempString);
			}
			
			f0.write(newLine);
			
			//Write Release Date
			f0.write("Release Date : ");
			String releaseDate = project.getProperties().getProperty(release);
			f0.write(releaseDate);
			f0.write(newLine);
						
			// Write Dependencies
			f0.write("Dependencies : ");
			for(int i=0;i<project.getDependencies().size();i++) {
				Dependency dependency = (Dependency)project.getDependencies().get(i);
				String tempString = " Dependency : " + dependency.getGroupId().toString() + "." + dependency.getArtifactId().toString();
				f0.write(tempString);
			}
			
			f0.write(newLine);
					
			// Write Plugins
			f0.write("Plugins : ");
			for(int i=0;i<project.getBuildPlugins().size();i++) {
				org.apache.maven.model.Plugin plugin = (org.apache.maven.model.Plugin)project.getBuildPlugins().get(i);
				String tempString = " Plugin : " + plugin.getArtifactId().toString();
				f0.write(tempString);
			}
			 			
			//Close File			
			f0.close();
			
		}
		catch (IOException e){
			System.out.println("An error occurred.");
		      e.printStackTrace();
		}
	}
}






