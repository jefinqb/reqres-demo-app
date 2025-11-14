pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {

        stage('Trigger Source (reqres-demo-app)') {
            steps {
                // This checkout is ONLY to bind the webhook to this job
                git branch: 'master',
                    url: 'https://github.com/jefinqb/reqres-demo-app.git'
            }
        }

        stage('Checkout Build Repo') {
            steps {
                // This is the repo you actually want to build
                git branch: 'commitmessages',
                    credentialsId: 'GitLab_Jefin',
                    url: 'git@code.qburst.com:jefin/api-devergence-predictor-tool.git'
            }
        }

        stage('Run Build') {
            steps {
                sh 'mvn clean compile exec:java'
            }
        }

        stage('Archive Output Files') {
            steps {
                archiveArtifacts artifacts: 'output/*', fingerprint: true
            }
        }

        stage('Send Email') {
            steps {
                emailext(
                    subject: 'Divergence Predictor Analyzer Deliverables',
                    to: 'jefin@qburst.com',
                    body: """\
The divergence predictor analyzer successfully analyzed your API implementation.
Please find the outputs attached.

Regards,
Jenkins
""",
                    attachmentsPattern: 'output/divergence_report.json, output/env.json, output/postman_collection.json, output/test-report.html'
                )
            }
        }
    }

    post {
        failure {
            emailext(
                subject: 'Divergence Predictor Analyzer Build FAILED',
                to: 'jefin@qburst.com',
                body: "The job failed. Please check Jenkins logs."
            )
        }
    }
}
