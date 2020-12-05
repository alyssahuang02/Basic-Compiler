import os
import re
import random

from cs50 import SQL
from flask import Flask, flash, redirect, render_template, request, session
from flask_session import Session
from tempfile import mkdtemp
from werkzeug.exceptions import default_exceptions, HTTPException, InternalServerError
from werkzeug.security import check_password_hash, generate_password_hash

from helpers import apology, login_required
from flask_mail import Mail, Message

# Configure application
app = Flask(__name__)
app.config["MAIL_DEFAULT_SENDER"] = os.getenv("MAIL_DEFAULT_SENDER")
app.config["MAIL_PASSWORD"] = os.getenv("MAIL_PASSWORD")
app.config["MAIL_PORT"] = 587
app.config["MAIL_SERVER"] = "smtp.gmail.com"
app.config['MAIL_USE_TLS'] = False
app.config['MAIL_USE_SSL'] = True
app.config["MAIL_USERNAME"] = os.getenv("MAIL_USERNAME")
app.config["MAIL_SUPPRESS_SEND"] = False
app.config["TESTING"] = False
mail = Mail(app)

# Ensure templates are auto-reloaded
app.config["TEMPLATES_AUTO_RELOAD"] = True

# Ensure responses aren't cached
@app.after_request
def after_request(response):
    response.headers["Cache-Control"] = "no-cache, no-store, must-revalidate"
    response.headers["Expires"] = 0
    response.headers["Pragma"] = "no-cache"
    return response

# Configure session to use filesystem (instead of signed cookies)
app.config["SESSION_FILE_DIR"] = mkdtemp()
app.config["SESSION_PERMANENT"] = False
app.config["SESSION_TYPE"] = "filesystem"
Session(app)

# Configure CS50 Library to use SQLite database
db = SQL("sqlite:///girlsangle.db")

@app.route("/", methods=["GET", "POST"])
@login_required
def index():
    admin = session['admin']
    if request.method == "POST":
        if admin == 0:
            name = request.form.get("name")
            if not name:
                return render_template("apology.html", top = 400, bottom = "Please enter your name.")
            note = request.form.get("note")
            if not note:
                note = ""
            week_id = request.form.get("week")
            db.execute("INSERT INTO signups (user_id, week_id, note) VALUES(?, ?, ?)",
                       session.get("user_id"), week_id, note)
        elif admin == 1:
            week = request.form.get("week")
            if not week:
                return render_template("apology.html", top = 400, bottom = "Please enter the week number.")
            month = request.form.get("month")
            if month == None:
                return render_template("apology.html", top = 400, bottom = "Please enter the month.")
            day = request.form.get("day")
            if not day:
                return render_template("apology.html", top = 400, bottom = "Please enter the day.")
            year = request.form.get("year")
            if not year == "2020" and not year == "2021":
                return render_template("apology.html", top = 400, bottom = "Please enter the year.")
            zoom_link = request.form.get("zoom-link")
            if not zoom_link:
                return render_template("apology.html", top = 400, bottom = "Please enter the Zoom link for this meeting.")
            #TODO: make this an update command instead to avoid duplicates
            db.execute("INSERT INTO weeks (id, year, month, day, zoom_link) VALUES(?, ?, ?, ?)",
                       week, month, day, zoom_link)
        return redirect("/")

    weeks_db = db.execute("SELECT DISTINCT(week_id) FROM signups ORDER BY week_id")
    weeks = []
    for elem in weeks_db:
        weeks.append(elem['week_id'])

    # a dictionary where week IDs are keys
    roster = {}
    for week in weeks:
        date_db = db.execute("SELECT month, day, year FROM weeks WHERE id= ?", week)
        month = date_db[0]['month']
        day = date_db[0]['day']
        year = date_db[0]['year']
        date = (month, day, year)
        signups_for_the_week = []
        signups_for_the_week.append(date)
        roster_element_db = db.execute("SELECT firstname, lastname, id FROM users WHERE id IN (SELECT user_id FROM signups WHERE week_id = ?)", week)
        for signup in roster_element_db:
            note_access = db.execute("SELECT note FROM signups WHERE user_id = ? AND week_id = ?", signup['id'], week)
            mentor_name = signup['firstname'] + ' ' + signup['lastname']
            note = note_access[0]['note']
            signup_tuple = (mentor_name, note)
            signups_for_the_week.append(signup_tuple)
        roster[week] = signups_for_the_week

    return render_template("index.html", roster=roster, weeks=weeks, admin=admin)

@app.route("/login", methods=["GET", "POST"])
def login():
    """Log user in"""
    # Forget any user_id
    session.clear()

    # User reached route via POST (as by submitting a form via POST)
    if request.method == "POST":

        # Ensure username was submitted
        if not request.form.get("username"):
            return render_template("apology.html", top = 400, bottom = "Please input a username.")

        # Ensure password was submitted
        elif not request.form.get("password"):
            return render_template("apology.html", top = 403, bottom = "Please provide a password.")

        # Query database for username
        rows = db.execute("SELECT * FROM users WHERE username = ?", request.form.get("username"))

        # Ensure username exists and password is correct
        if len(rows) != 1 or not check_password_hash(rows[0]["hash"], request.form.get("password")):
            return render_template("apology.html", top = 403, bottom = "Invalid username and/or password.")

        # Remember which user has logged in
        session["user_id"] = rows[0]["id"]
        session['admin'] = rows[0]['admin']

        # Redirect user to home page
        return redirect("/")

    # User reached route via GET (as by clicking a link or via redirect)
    else:
        return render_template("login.html")

@app.route("/logout")
def logout():
    """Log user out"""

    # Forget any user_id
    session.clear()

    # Redirect user to login form
    return redirect("/")

@app.route("/register", methods=["GET", "POST"])
def register():
    """Register user"""
    if request.method == "POST":

        # Checks for errors
        if not request.form.get("username"):
            return render_template("apology.html", top = 400, bottom = "Please input a username.")

        elif len(db.execute("SELECT * FROM users WHERE username = ?", request.form.get(
                "username"))) == 1:
            return render_template("apology.html", top = 400, bottom = "Username is already taken.")

        elif not request.form.get("password"):
            return render_template("apology.html", top = 400, bottom = "Must provide a password.")

        elif not request.form.get("confirmation") == request.form.get("password"):
            return render_template("apology.html", top = 400, bottom = "Passwords do not match.")

        elif not request.form.get("email"):
            return render_template("apology.html", top = 400, bottom = "Please input an email.")

        session['firstname'] = request.form.get("firstname")
        session['lastname'] = request.form.get("lastname")
        session['username'] =  request.form.get("username")
        session['password'] = generate_password_hash(request.form.get("password"))

        email = request.form.get("email")
        code = ""
        for i in range(6):
            code += str(random.randint(0, 9))

        with app.app_context():
            message = Message("Verification Email", recipients=['alyssahuang@college.harvard.edu'])
            message.body = "Please confirm your email by entering the following verification code: " + code
            app.logger.info('Processing default request')
            import socket
            server = socket.socket()
            server.bind(("0.0.0.0", 6677))
            mail.send(message)
            app.logger.info('Processing default request')

        session['email'] = email
        session['code'] = code

        return redirect("/verifyemail")
    else:
        return render_template("register.html")

@app.route("/verifyemail", methods=["GET", "POST"])
def verify_email():
    if request.method == "POST":
        if request.form["re-send"] == 'pressed':
            code = ""
            for i in range(6):
                code += str(random.randint(0, 9))
            message = Message("Please confirm your email by entering the following verification code: " + code, recipients=[session['email']])
            mail.send(message)
            session['code'] = code
            return redirect("/verifyemail")

        if not request.form.get("code") == session['code']:
            return render_template("apology.html", top = 400, bottom = "Incorrect verification code.")

        # Updates users database
        db.execute("INSERT INTO users (firstname, lastname, username, hash, account_type, email) VALUES (?, ?, ?, ?, ?, ?)",
            session['firstname'], session['lastname'], session['username'], session['password'], "Student", session['email'])

        # Clears session
        user_id = session["user_id"]
        session.clear()
        session["user_id"] = user_id

        # Let user know they're registered
        flash("Registered!")
    else:
        return render_template("verifyemail.html")


# @app.route("/mentorsignup", methods=["GET", "POST"])
# @login_required
# def mentor_signup():
#     if request.method == "POST":
#         name = request.form.get("name")
#         if not name:
#             return render_template("apology.html", top = 400, bottom = "Please enter your name.")
#         note = request.form.get("note")
#         if not note:
#             note = ""
#         week_id = 1
#         db.execute("INSERT INTO signups (user_id, week_id, note) VALUES(?, ?, ?)",
#                   session.get("user_id"), week_id, note)
#         return redirect("/")
#     return render_template("mentorsignup.html")

@app.route("/meetinfo")
@login_required
def meet_info():
    return render_template("meetinfo.html")

@app.route("/gameideas")
@login_required
def game_ideas():
    return render_template("gameideas.html")


@app.route("/summaries")
@login_required
def summaries():
    return render_template("summaries.html")

@app.route("/archive")
@login_required
def archive():
    return render_template("archive.html")