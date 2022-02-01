# Car Settings
## Preference Controller
The PreferenceController is a lifecycle-aware component that helps to encapsulate the business logic pertaining to specific Preferences. PreferenceControllers can only be attached to the relevant Preference through XML. Controllers are responsible for populating and modifying the presentation of an associated preference while responding to changes in system state.

Car Settings explicitly prevents the creation of PreferenceController through code in order to make it easier to modify the Settings hierarchy with minimal changes to the Java code.

### Useful inheritable methods
#### **checkInitialized()**
To throw IllegalStateException if any expected post-instantiation setup is not completed using SettingsFragment.use(Class, int) prior to associating the controller with its preference. This will be called before the controller lifecycle begins.

#### **onCreateInternal()**
To complete any operations needed at creation time e.g. loading static configuration.

#### **onStartInternal()**
To complete any operations needed each time the controller is started e.g. registering broadcast receivers.

#### **onResumeInternal()**
To complete any operations needed each time the controller is resumed. Prefer to use onStartInternal() unless absolutely necessary as controllers may not be resumed in a multi-display scenario.

#### **onPauseInternal()**
To complete any operations needed each time the controller is paused. Prefer to use onStartInternal() unless absolutely necessary as controllers may not be resumed in a multi-display scenario.

#### **onStopInternal()**
To complete any operations needed each time the controller is stopped e.g. unregistering broadcast receivers.
 
#### **onDestroyInternal()**
To complete any operations needed when the controller is destroyed e.g. freeing up held resources.

#### **getAvailabilityStatus()**
Returns the AvailabilityStatus for the setting. This status is used to determine if the setting should be shown, hidden, or disabled. Defaults to *AVAILABLE*. This will be called before the controller lifecycle begins and on refresh events.

#### **updateState(Preference)**
To update the presentation of the preference for the current system state (summary, switch state, etc). If the preference has dynamic content (such as preferences added to a group), it may be updated here as well. Operations should be idempotent as this may be called multiple times. This will only be called when the following are true: getAvailabilityStatus() returns *AVAILABLE* and onCreateInternal() has completed.

#### **onApplyUxRestrictions(CarUxRestrictions)**
Updates the preference enabled status given the RestrictionInfo. This will be called before the controller lifecycle begins and on refresh events. The preference is disabled by default when  CarUxRestrictions#UX_RESTRICTIONS_NO_SETUP is set in UxRestrictions. Subclasses may override this method to modify enabled state based on additional driving restrictions.

#### **handlePreferenceChanged(Preference, Object)**
Called when the associated preference is changed by the user. This is called before the state of the preference is updated and before the state is persisted. Parâmetros:
- preference (Preference): the changed preference.
- newValue (Object): the new value of the preference.

Return true to update the state of the preference with the new value.

#### **handlePreferenceClicked(Preference)**
Called when the preference associated with this controller is clicked. Subclasses may choose to handle the click event.  Return true if click is handled and further propagation should cease.
