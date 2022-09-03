properties([
    parameters([
        choice(
            description: "Choose test suite",
            choices: ['ALL','API','UI','VALIDATION'],
            name: 'TEST_SUITE'
            ),
         choice(
            description: "Choose browser",
            choices: ['chrome','firefox'],
            name: 'BROWSER_NAME'
            ),
        string(
            description: "Choose threads number",
            defaultValue: '2',
            name: 'THREADS'
            ),
        string(
            description: "Choose remote url",
            defaultValue: 'http://167.71.48.36:8080/wd/hub',
            name: 'REMOTE_URL'
            )
    ])
])

def suiteName = params.TEST_SUITE
def browser = params.BROWSER_NAME
def threads = params.THREADS
def remoteUrl = params.REMOTE_URL
def build_ok = true

node {
    stage("Checkout Repo") {
        git branch: 'master',
        credentialsId: '1dbdf731-0ac0-4cfa-97d4-ebe3de88835a',
        url: 'https://gitlab.ciklum.net/mentoring/bukachv/automationpoc.git'
    }

    stage("Build") {
        sh "./gradlew clean assemble"
    }

    stage("SonarQube Analysis") {
        withSonarQubeEnv('SonarQube') {
            sh './gradlew sonarqube'
        }
    }

    stage("Quality Gate") {
        timeout(time: 1, unit: 'HOURS') {
            def qg = waitForQualityGate()
              if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
            }
        }
    }

   if (suiteName == "VALIDATION" || suiteName == "ALL") {
        try {
            stage("Json Validation Tests") {
                sh "./gradlew json_validation -Dbrowser.name=${browser} -Dthreads=${threads} -Dweb.remote.driver.url=${remoteUrl}"
                sh 'exit 0'
            }
        }   catch (e) {
                build_ok = false
            }
    } else {
         echo "skipped stage VALIDATION"
    }

    if (suiteName == "API" || suiteName == "ALL") {

        try {
            stage("API Tests") {
                sh "./gradlew api -Dthreads=${threads} -Dweb.remote.driver.url=${remoteUrl}"
                sh 'exit 0'
            }
        }   catch (e) {
              build_ok = false
            }
    } else {
        echo  "skipped stage API"
    }

    if (suiteName == "UI" || suiteName == "ALL") {
        try {
            stage("UI Tests") {
                sh "./gradlew web -Dbrowser.name=${browser} -Dthreads=${threads} -Dweb.remote.driver.url=${remoteUrl}"
                sh 'exit 0'
            }
        }   catch (e) {
                build_ok = false
            }
    } else {
         echo "skipped stage UI"
    }

    stage('Reports') {
        allure([
            includeProperties: false,
            jdk: '',
            properties: [],
            reportBuildPolicy: 'ALWAYS',
            results: [[path: 'build/allure-results']]
        ])
    }

    if (build_ok) {
            currentBuild.result = "SUCCESS"
        } else {
            currentBuild.result = "FAILURE"
    }
}
