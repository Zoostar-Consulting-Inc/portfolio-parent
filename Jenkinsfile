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
		
        stage('Install') {
			steps {
				script {
					if("support" == "$target" &&
							("opened" == "$action" || "synchronize" == "$action")) {
						bat 'mvn -U -B clean install -Dbuild.number="%BUILD_NUMBER%" -Dmaven.test.skip=true -Dtomcat.maven.deploy.phase="install"'
					}
				}
			}
		}
		
        stage('Deploy') {
			steps {
				script {
					if("closed" == "$action" &&
							("develop" == "$target" || "support" == "$target")) {
						bat 'mvn -Pdevelop -U -B clean deploy -Dbuild.number="%BUILD_NUMBER%" -Dmaven.test.skip=true -Dtomcat.maven.deploy.phase="install"'
					}
				}
			}
		}
    }
    
}