package pl.edu.pw.ii.entityminer.database.datasource

/**
 * Created with IntelliJ IDEA.
 * User: Rafael Hazan
 * Date: 7/3/13
 * Time: 11:35 PM
 */
case class ConferenceData (
  private var _uri: String,

  private var _name: String,
  private var _date: String,
  private var _place: String,
  private var _nameAbbreviation: String,
  private var _paperSubmissionDate: String,
  private var _paperNotificationDate: String,
  private var _paperFinalVersionDate: String,
  private var _paperSubmissionAbstract: String,
  private var _registrationDate: String,
  private var _acceptanceDate: String,
  private var _cameraReadyDate: String,
  private var _committee: String
  ) {
  def this() = this(null, null, null, null, null, null, null, null, null, null, null, null, null)

  def uri = _uri
  def uri_=(uri: String) = _uri = uri
  def date = _date
  def date_=(date: String) = _date = date
  def place = _place
  def place_=(place: String) = _place = place
  def name = _name
  def name_=(name: String) = _name = name
  def nameAbbreviation = _nameAbbreviation
  def nameAbbreviation_=(nameAbbreviation: String) = _nameAbbreviation = nameAbbreviation
  def paperSubmissionDate = _paperSubmissionDate
  def paperSubmissionDate_=(paperSubmissionDate: String) = _paperSubmissionDate = paperSubmissionDate
  def paperNotificationDate = _paperNotificationDate
  def paperNotificationDate_=(paperNotificationDate: String) = _paperNotificationDate = paperNotificationDate
  def paperFinalVersionDate = _paperFinalVersionDate
  def paperFinalVersionDate_=(paperFinalVersionDate: String) = _paperFinalVersionDate = paperFinalVersionDate
  def paperSubmissionAbstract = _paperSubmissionAbstract
  def paperSubmissionAbstract_=(paperSubmissionAbstract: String) = _paperSubmissionAbstract = paperSubmissionAbstract
  def registrationDate = _registrationDate
  def registrationDate_=(registrationDate: String) = _registrationDate = registrationDate
  def acceptanceDate = _acceptanceDate
  def acceptanceDate_=(acceptanceDate: String) = _acceptanceDate = acceptanceDate
  def cameraReadyDate = _cameraReadyDate
  def cameraReadyDate_=(cameraReadyDate: String) = _cameraReadyDate = cameraReadyDate
  def committee = _committee
  def committee_=(committee: String) = _committee = committee
}
