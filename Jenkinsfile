pipeline {
	agent any
	tools {
    	maven 'maven 3.9.6' 
    }
    	
   	stages {
        stage('Verify') {
			steps {
				script {
					if("support" != "$target" &&
							("create" == "$action"|| "opened" == "$action" || "synchronize" == "$action")) {
						bat 'mvn -U clean verify -Dbuild.number=%BUILD_NUMBER%'
					}
				}
			}
		}
		
        stage('Install to Stage') {
			steps {
				script {
					if("support" == "$target" &&
							("opened" == "$action" || "synchronize" == "$action")) {
						bat 'mvn -Pstage -U -B clean install -Dbuild.number="%BUILD_NUMBER%" -Dmaven.test.skip=true -Dtomcat.maven.deploy.phase="install"'
					}
				}
			}
		}
		
        stage('Deploy to Dev') {
			steps {
				script {
					if("closed" == "$action" && "develop" == "$target") {
						bat 'mvn -Pdev -U -B clean deploy -Dbuild.number="%BUILD_NUMBER%" -Dmaven.test.skip=true -Dtomcat.maven.deploy.phase="install"'
					}
				}
			}
		}
		
        stage('Deploy to Prod') {
			steps {
				script {
					if("closed" == "$action" && "support" == "$target") {
						bat 'mvn -Pprod -U -B clean deploy -Dbuild.number="%BUILD_NUMBER%" -Dmaven.test.skip=true -Dtomcat.maven.deploy.phase="install"'
					}
				}
			}
		}

    }    
}